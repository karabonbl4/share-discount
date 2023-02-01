package com.senla.repository.mapper;

import com.senla.model.Coupon;
import com.senla.model.Trademark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class CouponMapper {

    public Coupon convertToCoupon(ResultSet resultSet){
        Coupon coupon = new Coupon();
        try {
            coupon.setId(Long.parseLong(resultSet.getString(1)));
            coupon.setName(resultSet.getString(2));
            coupon.setStartDate(resultSet.getDate(3));
            coupon.setEndDate(resultSet.getDate(4));
            coupon.setDiscount(resultSet.getBigDecimal(5) );
            coupon.setUsed(resultSet.getBoolean(6));
            Long l = resultSet.getLong(7);
            Trademark trademark = new Trademark();
            trademark.setId(l);
            coupon.setTrademarkId(trademark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coupon;
    }
}
