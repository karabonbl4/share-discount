package com.senla.model.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "fetch.purchase",
        attributeNodes = @NamedAttributeNode("purchase"))
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;
    @Column
    private BigDecimal discount;
    @Column
    private Boolean used;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "trademark_id")
    private Trademark trademark;
    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "coupon")
    private Purchase purchase;
    @ManyToMany
    @JoinTable(name = "user_coupon", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

}
