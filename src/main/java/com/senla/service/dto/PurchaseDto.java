package com.senla.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private Long id;
    private String name;
    private Date transactionDate;
    private BigDecimal sum;
    private UserDto userId;
    private DiscountCardDto cardId;
    private CouponDto couponId;
}
