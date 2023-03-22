package com.senla.model.dto.save;

import com.senla.model.dto.DiscountPolicyDto;
import com.senla.model.dto.TrademarkDto;
import com.senla.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCardWithPolicy {
    private Long id;
    private String name;
    private Long number;
    private BigDecimal discount;
    private Boolean isConfirm;
    private Boolean isRent;
    private DiscountPolicyDto policy;
    private UserDto ownerUser;
}
