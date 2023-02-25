package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
    @JsonIgnore
    private List<PurchaseDto> purchases;
    @JsonIgnore
    private List<RoleDto> roles;
    @JsonIgnore
    private List<CouponDto> coupons;
    private String username;
    private String password;
    private List<DiscountCardDto> cards;
}
