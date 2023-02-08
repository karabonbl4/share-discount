package com.senla.repository.mapper;

import com.senla.model.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CouponMapper {

    public Coupon convertToCoupon(@NotNull ResultSet resultSet) {
        Coupon coupon = new Coupon();
        try {
            coupon.setId(Long.parseLong(resultSet.getString(1)));
            coupon.setName(resultSet.getString(2));
            coupon.setStartDate(resultSet.getDate(3));
            coupon.setEndDate(resultSet.getDate(4));
            coupon.setDiscount(resultSet.getBigDecimal(5));
            coupon.setUsed(resultSet.getBoolean(6));
            Trademark trademark = new Trademark();
            trademark.setId(resultSet.getLong(7));
            trademark.setTitle(resultSet.getString(9));
            coupon.setTrademarkId(trademark);
            User user = User.builder()
                .id(resultSet.getLong(12))
                .firstName(resultSet.getString(13))
                    .surName(resultSet.getString(14))
                    .phoneNumber(resultSet.getString(15))
                    .email(resultSet.getString(16))
                    .birthday(Objects.requireNonNullElse(resultSet.getDate(17).toLocalDate(), LocalDate.now()))
                    .score(resultSet.getBigDecimal(18))
                    .isActive(resultSet.getBoolean(19))
                    .build();

            List<User> users = new ArrayList<>();
            users.add(user);
            coupon.setUsers(users);
            Purchase purchase = new Purchase();
            purchase.setId(resultSet.getLong(20));
            purchase.setName(resultSet.getString(21));
            purchase.setTransactionDate(resultSet.getDate(22));
            purchase.setSum(resultSet.getBigDecimal(23));

            if (resultSet.getLong(24) == user.getId()) {
                purchase.setUserId(user);
            } else {
                User newUser = User.builder()
                        .id(resultSet.getLong(27))
                        .firstName(resultSet.getString(28))
                        .surName(resultSet.getString(29))
                        .phoneNumber(resultSet.getString(30))
                        .email(resultSet.getString(31))
                        .birthday(Objects.requireNonNull(resultSet.getDate(32).toLocalDate()))
                        .score(resultSet.getBigDecimal(33))
                        .isActive(resultSet.getBoolean(34))
                        .build();
                purchase.setUserId(newUser);
            }
            DiscountCard discountCard = DiscountCard.builder()
                    .id(resultSet.getLong(35))
                    .name(resultSet.getString(36))
                    .number(resultSet.getLong(37))
                    .discount(resultSet.getBigDecimal(38))
                    .ownerId(user)
                    .build();
            DiscountPolicy dp = DiscountPolicy.builder()
                    .id(resultSet.getLong(41))
                    .title(resultSet.getString(42))
                    .minDiscount(resultSet.getBigDecimal(43))
                    .maxDiscount(resultSet.getBigDecimal(44))
                    .discountStep(resultSet.getBigDecimal(45))
                    .trademarkId(trademark)
                    .build();
            discountCard.setDiscountPolicyId(dp);
            purchase.setCardId(discountCard);
            List<Purchase> purchases = new ArrayList<>();
            purchases.add(purchase);
            coupon.setPurchases(purchases);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coupon;
    }
}
