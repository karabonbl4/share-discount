package com.senla.model.dto.save;

import com.senla.model.dto.TrademarkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPolicyForSave {

    private Long id;

    private String title;

    private BigDecimal minDiscount;

    private BigDecimal maxDiscount;

    private BigDecimal discountStep;

    private TrademarkDto trademark;
}
