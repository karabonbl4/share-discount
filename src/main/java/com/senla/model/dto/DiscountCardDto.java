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
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCardDto {
    public DiscountCardDto(Long id){
        this.id = id;
    }
    private Long id;
    private String name;
    private Long number;
    private BigDecimal discount;
    @JsonIgnore
    private UserDto ownerId;
    private DiscountPolicyDto discountPolicyId;
    @JsonIgnore
    private List<PurchaseDto> purchases;
}
