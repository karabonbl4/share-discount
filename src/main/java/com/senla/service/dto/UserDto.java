package com.senla.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String surName;
    private String phoneNumber;
    private String email;
    private LocalDate birthday;
    private BigDecimal score;
    private Boolean isActive;
    private List<PurchaseDto> purchases;
    private List<RoleDto> roles;
    private List<CouponDto> coupons;
    private List<DiscountCardDto> cards;
}
