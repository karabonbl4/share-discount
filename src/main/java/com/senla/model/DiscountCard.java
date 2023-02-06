package com.senla.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountCard extends Entity {
    private Long id;
    private String name;
    private Long number;
    private BigDecimal discount;
    private User ownerId;
    private DiscountPolicy discountPolicyId;
    private List<Purchase> purchases;
}
