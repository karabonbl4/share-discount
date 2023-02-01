package com.senla.model;

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
public class Purchase extends Entity {
    private Long id;
    private String name;
    private Date transactionDate;
    private BigDecimal sum;
    private User userId;
    private DiscountCard cardId;
    private Coupon couponId;

}
