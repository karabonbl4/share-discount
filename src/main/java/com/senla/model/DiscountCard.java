package com.senla.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "discount_card")
public class DiscountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Long number;
    @Column
    private BigDecimal discount;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User ownerId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_policy_id")
    private DiscountPolicy discountPolicyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardId")
    private List<Purchase> purchases;
}
