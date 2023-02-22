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
@Table(name = "discount_policy")
public class DiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column(name = "min_discount")
    private BigDecimal minDiscount;
    @Column(name = "max_discount")
    private BigDecimal maxDiscount;
    @Column(name = "discount_step")
    private BigDecimal discountStep;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "trademark_id")
    private Trademark trademarkId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discountPolicyId")
    private List<DiscountCard> discountCards;
}
