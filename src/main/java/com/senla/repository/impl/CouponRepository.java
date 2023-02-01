package com.senla.repository.impl;

import com.senla.config.ConnectionHolder;
import com.senla.model.Coupon;
import com.senla.model.Trademark;
import com.senla.repository.CustomRepository;
import com.senla.repository.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class CouponRepository implements CustomRepository<Coupon> {

    private static final String SELECT_BY_ID = "select * from coupon where id=";
    private static final String UPDATE_COUPON = "update coupon set name=?, start_date=?, end_date=?, discount=?, used=?, trademark_id=? where id=?";
    private static final String INSERT_INTO_COUPON = "insert into coupon (id, name, start_date, end_date, discount, used, trademark_id) values(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_COUPON = "delete from coupon where id = ?";
    private final ConnectionHolder connectionHolder;
    private final CouponMapper couponMapper;
    private final TrademarkRepository trademarkRepository;


    @Override
    public Coupon findById(Long id) {
        Connection connection = connectionHolder.getConnection();
        Coupon coupon = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID + id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                coupon = couponMapper.convertToCoupon(resultSet);
                Long id1 = coupon.getTrademarkId().getId();
                Trademark byId = trademarkRepository.findForProperty(id1);
                coupon.setTrademarkId(byId);
            }
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
