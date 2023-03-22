package com.senla.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCardDto {

    private Long id;

    private String name;

    private Long number;

    private BigDecimal discount;

    private Boolean isConfirm;

    private Boolean isRent;
}
