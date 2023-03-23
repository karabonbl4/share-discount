package com.senla.model.entity;

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
@NamedEntityGraph(name = "fetch.discount-card.policy",
        attributeNodes = @NamedAttributeNode(value = "discountPolicy"))
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

    @Column(name = "is_confirm")
    private Boolean isConfirm;

    @Column(name = "is_rent")
    private Boolean isRent;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_policy_id")
    private DiscountPolicy discountPolicy;

    @OneToMany(targetEntity = Rent.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "card")
    private List<Rent> rents;

    @OneToMany(targetEntity = Purchase.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "card")
    private List<Purchase> purchases;
}
