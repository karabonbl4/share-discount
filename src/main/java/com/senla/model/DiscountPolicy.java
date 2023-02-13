package com.senla.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountPolicy extends Entity {
    private Long id;
    private String title;
    private BigDecimal minDiscount;
    private BigDecimal maxDiscount;
    private BigDecimal discountStep;
    private Trademark trademarkId;
    private List<DiscountCard> discountCards;
}
