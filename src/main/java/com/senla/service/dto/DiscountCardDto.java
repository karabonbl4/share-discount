package com.senla.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCardDto {
    private Long id;
    private String name;
    private Long number;
    private BigDecimal discount;
    private UserDto ownerId;
    private DiscountPolicyDto discountPolicyId;
    private List<PurchaseDto> purchases;
}
