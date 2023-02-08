package com.senla.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Entity {
    private Long id;
    private String firstName;
    private String surName;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private BigDecimal score;
    private Boolean isActive;
    private List<Purchase> purchases;
    private List<Role> roles;
    private List<Coupon> coupons;
    private List<DiscountCard> cards;
}
