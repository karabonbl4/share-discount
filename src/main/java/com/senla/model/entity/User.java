package com.senla.model.entity;

import com.senla.model.entity.*;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String surName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String email;
    @Column(columnDefinition = "DATE")
    private LocalDate birthday;
    @Column
    private BigDecimal score;
    @Column(name = "is_active")
    private Boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Purchase> purchases;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<Coupon> coupons;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<DiscountCard> cards;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "admin_trademark", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "trademark_id"))
    private Set<Trademark> trademarks;

}
