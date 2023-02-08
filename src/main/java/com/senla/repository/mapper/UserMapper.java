package com.senla.repository.mapper;

import com.senla.model.DiscountCard;
import com.senla.model.DiscountPolicy;
import com.senla.model.Trademark;
import com.senla.model.User;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class UserMapper {
    @SneakyThrows
    public User convertToUserDto(ResultSet resultSet) {
        User user = User.builder()
                .id(resultSet.getLong(1))
                .firstName(resultSet.getString(2))
                .surName(resultSet.getString(3))
                .phoneNumber(resultSet.getString(4))
                .email(resultSet.getString(5))
                .birthday(Objects.requireNonNullElse(resultSet.getDate(6), Date.valueOf(LocalDate.now())))
                .score(resultSet.getBigDecimal(7))
                .isActive(resultSet.getBoolean(8))
                .build();
        Trademark trademark = Trademark.builder()
                .id(resultSet.getLong(21))
                .title(resultSet.getString(22))
                .build();
        DiscountPolicy discountPolicy = DiscountPolicy.builder()
                .id(resultSet.getLong(15))
                .title(resultSet.getString(16))
                .minDiscount(resultSet.getBigDecimal(17))
                .maxDiscount(resultSet.getBigDecimal(18))
                .discountStep(resultSet.getBigDecimal(19))
                .trademarkId(trademark)
                .build();
        DiscountCard discountCard = DiscountCard.builder()
                .id(resultSet.getLong(9))
                .name(resultSet.getString(10))
                .number(resultSet.getLong(11))
                .discount(resultSet.getBigDecimal(12))
                .discountPolicyId(discountPolicy)
                .ownerId(user)
                .build();
        user.setCards(List.of(discountCard));
        return user;
    }
}
