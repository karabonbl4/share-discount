package com.senla.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"user\"")
@NamedEntityGraph(name = "fetch.roles",
                  attributeNodes = @NamedAttributeNode(value = "roles"))
public class User{
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

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "buyer", fetch = FetchType.LAZY)
    private List<Purchase> purchases;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Coupon> coupons;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "owner", fetch = FetchType.LAZY)
    private List<DiscountCard> cards;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "tenant")
    private List<Rent> rents;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "admin_trademark", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "trademark_id"))
    private List<Trademark> trademarks;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column()
    private boolean enabled;

}
