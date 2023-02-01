package com.senla.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrademarkDto {
    private Long id;
    private String title;
    private List<UserDto> admins;
    private List<CouponDto> coupons;
    private List<DiscountPolicyDto> discountPolicies;
}
