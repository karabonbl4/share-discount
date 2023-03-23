package com.senla.model.dto.rent;

import com.senla.model.dto.RentDto;
import com.senla.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountCardForRentDto {

    private Long id;

    private String name;

    private Long number;

    private BigDecimal discount;

    private Boolean isConfirm;

    private Boolean isRent;

    private UserDto owner;

    private RentDto rent;
}
