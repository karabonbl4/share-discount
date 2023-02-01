package com.senla.repository.impl;

import com.senla.config.ConnectionHolder;
import com.senla.model.Coupon;
import com.senla.model.Trademark;
import com.senla.repository.CustomRepository;
import com.senla.repository.mapper.CouponMapper;
import com.senla.repository.mapper.TrademarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class TrademarkRepository implements CustomRepository<Trademark> {
    private static final String SELECT_BY_ID = "select * from trademark where id=?";
    private static final String INSERT_INTO_TRADEMARK = "insert into trademark (id, title) values(?, ?)";
    private static final String UPDATE_TRADEMARK = "update trademark set title = ? where id=?";
    private static final String SELECT_COUPONS_BY_TRADEMARK_ID = "select * from coupon where trademark_id=?";
    private static final String DELETE_TRADEMARK = "delete from trademark where id = ?";
    private final TrademarkMapper trademarkMapper;
    private final ConnectionHolder connectionHolder;
    private final CouponMapper couponMapper;

    @Override
    public Trademark findById(Long id) {
        Connection connection = connectionHolder.getConnection();
        Trademark trademark = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trademark = trademarkMapper.convertToTrademark(resultSet);
            }
            statement.close();

            PreparedStatement statement1 = connection.prepareStatement(SELECT_COUPONS_BY_TRADEMARK_ID);
            statement1.setInt(1, Math.toIntExact(id));
            ResultSet resultSet1 = statement1.executeQuery();
            if (resultSet1 != null) {
                List<Coupon> coupons = new ArrayList<>();
                while (resultSet1.next()) {
                    Coupon coupon = couponMapper.convertToCoupon(resultSet1);
                    coupons.add(coupon);
                }
                assert trademark != null;
                trademark.setCoupons(coupons);
            }
            statement1.close();

            return trademark;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Trademark saveOrUpdate(Trademark trademark) {
        Connection connection = connectionHolder.getConnection();
        try {
            if (findById(trademark.getId()) == null) {
                PreparedStatement statement = connection.prepareStatement(INSERT_INTO_TRADEMARK);
                statement.setLong(1, trademark.getId());
                statement.setString(2, trademark.getTitle());
                statement.executeUpdate();
                statement.close();
            } else {
                PreparedStatement statementUpdate = connection.prepareStatement(UPDATE_TRADEMARK);
                statementUpdate.setString(1, trademark.getTitle());
                statementUpdate.setLong(2, trademark.getId());
                statementUpdate.executeUpdate();
                statementUpdate.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return trademark;
    }


    @Override
    public void delete(Trademark trademark) {
        Connection connection = connectionHolder.getConnection();
        try {
            PreparedStatement statementUpdate = connection.prepareStatement(DELETE_TRADEMARK);
            statementUpdate.setLong(1, trademark.getId());
            statementUpdate.executeUpdate();
            statementUpdate.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Trademark findForProperty(Long id) {
        Connection connection = connectionHolder.getConnection();
        Trademark trademark = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trademark = trademarkMapper.convertToTrademark(resultSet);
            }
            statement.close();
            return trademark;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
