package com.senla.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase extends Entity {
    private Long id;
    private String name;
    private Date transactionDate;
    private BigDecimal sum;
    private User userId;
    private DiscountCard cardId;
    private Coupon couponId;

}
