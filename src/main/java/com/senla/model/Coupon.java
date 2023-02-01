package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends Entity {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private BigDecimal discount;
    private Boolean used;
    private Trademark trademarkId;
    private List<Purchase> purchases;
    private List<User> users;

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
