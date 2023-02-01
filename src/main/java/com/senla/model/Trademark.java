package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trademark extends Entity {
    private Long id;
    private String title;
    private List<User> admins;
    private List<Coupon> coupons;
    private List<DiscountPolicy> discountPolicies;

}
