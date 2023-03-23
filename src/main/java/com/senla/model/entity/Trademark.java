package com.senla.model.entity;

import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

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
    private List<User> admins;
    @OneToMany(mappedBy = "trademark", fetch = FetchType.LAZY)
    private List<Coupon> coupons;
    @OneToMany(mappedBy = "trademark", fetch = FetchType.LAZY)
    private List<DiscountPolicy> discountPolicies;

}
