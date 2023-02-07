package com.senla.model;

import lombok.*;

import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trademark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "trademarks")
    private List<User> admins;
    @OneToMany(mappedBy = "trademarkId")
    private List<Coupon> coupons;
    @OneToMany(mappedBy = "trademarkId")
    private List<DiscountPolicy> discountPolicies;

}
