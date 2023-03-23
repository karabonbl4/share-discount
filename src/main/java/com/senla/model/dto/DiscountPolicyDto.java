package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
}
