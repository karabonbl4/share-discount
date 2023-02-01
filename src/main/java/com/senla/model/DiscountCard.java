package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard extends Entity {
    private Long id;
    private String name;
    private Long number;
    private BigDecimal discount;
    private User ownerId;
    private DiscountPolicy discountPolicyId;
    private List<Purchase> purchases;
}