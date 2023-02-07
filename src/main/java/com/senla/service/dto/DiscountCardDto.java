package com.senla.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<PurchaseDto> purchases;
}
