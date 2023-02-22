package com.senla.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<UserDto> admins;
    @JsonIgnore
    private List<CouponDto> coupons;
    @JsonIgnore
    private List<DiscountPolicyDto> discountPolicies;
}
