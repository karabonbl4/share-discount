package com.senla.model.dto.save;

import com.senla.model.dto.RoleDto;
import com.senla.model.dto.TrademarkDto;
import com.senla.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPurchaseServiceDto extends UserDto {
    private Long id;

    private String firstName;

    private String surName;

    private String phoneNumber;

    private String email;

    private Boolean isActive;

    private String username;

    private String password;

    private TrademarkDto trademark;

}
