package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
