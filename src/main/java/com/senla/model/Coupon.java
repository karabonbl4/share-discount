package com.senla.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column
    private BigDecimal discount;
    @Column
    private Boolean used;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trademark_id")
    private Trademark trademarkId;
    @OneToMany(mappedBy = "couponId")
    private List<Purchase> purchases;
    @ManyToMany
    @JoinTable(name = "user_coupon", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
