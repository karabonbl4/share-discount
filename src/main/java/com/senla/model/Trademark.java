package com.senla.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trademark extends Entity {
    private Long id;
    private String title;
    private List<User> admins;
    private List<Coupon> coupons;
    private List<DiscountPolicy> discountPolicies;

    public Trademark(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
