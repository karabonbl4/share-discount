package com.senla.service.dto;

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
public class DiscountPolicyDto {
    private Long id;
    private String title;
    private BigDecimal minDiscount;
    private BigDecimal maxDiscount;
    private BigDecimal discountStep;
    private TrademarkDto trademarkId;
    private List<DiscountCardDto> discountCards;
}
