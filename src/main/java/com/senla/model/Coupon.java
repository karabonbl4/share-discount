package com.senla.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
