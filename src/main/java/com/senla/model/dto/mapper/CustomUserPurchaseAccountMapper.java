package com.senla.model.dto.mapper;

import com.senla.model.dto.save.UserPurchaseServiceDto;
import com.senla.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomUserPurchaseAccountMapper {

    public User map(@NotNull UserPurchaseServiceDto purchaseAccount){
        return User.builder()
                .firstName(purchaseAccount.getFirstName())
                .surName(purchaseAccount.getSurName())
                .email(purchaseAccount.getEmail())
                .phoneNumber(purchaseAccount.getPhoneNumber())
                .username(purchaseAccount.getUsername())
                .password(purchaseAccount.getPassword())
                .build();
    }
}
