package com.senla.repository.impl;

import com.senla.config.ConnectionHolder;
import com.senla.model.Coupon;
import com.senla.model.Purchase;
import com.senla.model.Trademark;
import com.senla.model.User;
import com.senla.repository.CustomRepository;
import com.senla.repository.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CouponRepository implements CustomRepository<Coupon> {

    private static final String SELECT_BY_ID = "SELECT * FROM coupon c \n" +
            "\tJOIN trademark t ON c.trademark_id=t.id \n" +
            "\tLEFT OUTER JOIN user_coupon uc ON c.id=uc.coupon_id \n" +
            "\tLEFT OUTER JOIN \"user\" u ON uc.user_id=u.id \n" +
            "\tJOIN purchase p ON p.coupon_id=c.id \n" +
            "\tJOIN \"user\" us ON p.user_id=us.id\n" +
            "\tJOIN discount_card dc ON p.card_id=dc.id \n" +
            "\tJOIN discount_policy dp ON dc.discount_policy_id=dp.id \n" +
            "\tWHERE c.id=?";
    private static final String UPDATE_COUPON = "update coupon set name=?, start_date=?, end_date=?, discount=?, used=?, trademark_id=? where id=?";
    private static final String INSERT_INTO_COUPON = "insert into coupon (id, name, start_date, end_date, discount, used, trademark_id) values(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_COUPON = "delete from coupon where id = ?";
    private final ConnectionHolder connectionHolder;
    private final CouponMapper couponMapper;


    @Override
    public Coupon findById(Long id) {
        Connection connection = connectionHolder.getConnection();
        Coupon coupon = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Purchase> purchases = new HashSet<>();
            Set<User> users = new HashSet<>();
            while (resultSet.next()){
                coupon = couponMapper.convertToCoupon(resultSet);
                users.add(coupon.getUsers().get(0));
                purchases.add(coupon.getPurchases().get(0));
            }
            Objects.requireNonNull(coupon).setPurchases(purchases.stream().collect(Collectors.toList()));
            Objects.requireNonNull(coupon).setUsers(users.stream().collect(Collectors.toList()));
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return coupon;
    }

    @Override
    public Coupon saveOrUpdate(Coupon coupon) {
        Connection connection = connectionHolder.getConnection();
        try {
            if (findById(coupon.getId()) == null) {
                PreparedStatement statement = connection.prepareStatement(INSERT_INTO_COUPON);
                statement.setLong(1, coupon.getId());
                statement.setString(2, coupon.getName());
                statement.setDate(3, new Date(coupon.getStartDate().getTime()));
                statement.setDate(4, new Date(coupon.getEndDate().getTime()));
                statement.setBigDecimal(5, coupon.getDiscount());
                statement.setBoolean(6, coupon.getUsed());
                statement.setLong(7, coupon.getTrademarkId().getId());
                statement.executeUpdate();
                statement.close();
            } else {
                PreparedStatement statement = connection.prepareStatement(UPDATE_COUPON);
                statement.setString(1, coupon.getName());
                statement.setDate(2, new Date(coupon.getStartDate().getTime()));
                statement.setDate(3, new Date(coupon.getEndDate().getTime()));
                statement.setBigDecimal(4, coupon.getDiscount());
                statement.setBoolean(5, coupon.getUsed());
                if(coupon.getTrademarkId() == null){
                    Trademark trademarkId = findById(coupon.getId()).getTrademarkId();
                    statement.setLong(6, trademarkId.getId());}
                statement.setLong(7, coupon.getId());
                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return coupon;
    }

    @Override
    public void delete(Coupon coupon) {
        Connection connection = connectionHolder.getConnection();
        try {
            PreparedStatement statementDelete = connection.prepareStatement(DELETE_COUPON);
            statementDelete.setLong(1, coupon.getId());
            statementDelete.executeUpdate();
            statementDelete.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
