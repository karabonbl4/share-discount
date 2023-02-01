package com.senla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPolicy extends Entity {
    private Long id;
    private String title;
    private BigDecimal minDiscount;
    private BigDecimal maxDiscount;
    private BigDecimal discountStep;
    private Trademark trademarkId;
    private List<DiscountCard> discountCards;
}
