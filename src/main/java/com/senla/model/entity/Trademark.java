package com.senla.model.entity;

import lombok.*;

import java.util.List;
import java.util.Set;
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
    private Set<User> admins;
    @OneToMany(mappedBy = "trademark")
    private Set<Coupon> coupons;
    @OneToMany(mappedBy = "trademark")
    private Set<DiscountPolicy> discountPolicies;

}
