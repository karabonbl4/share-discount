package com.senla.model.dto.mapper;


import com.senla.model.dto.DiscountCardDto;
import com.senla.model.dto.DiscountPolicyDto;
import com.senla.model.dto.TrademarkDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.rent.DiscountCardForRentDto;
import com.senla.model.dto.save.DiscountCardWithTrademarkDto;
import com.senla.model.entity.DiscountCard;
import com.senla.model.entity.DiscountPolicy;
import com.senla.model.entity.Rent;
import com.senla.model.entity.User;
import com.senla.service.TrademarkService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class CustomDiscountCardMapper {

    private final ModelMapper modelMapper;

    private final TrademarkService trademarkService;

    public DiscountCardWithTrademarkDto map(DiscountCard discountCard) {
        TrademarkDto trademarkByPolicyId = trademarkService.findByPolicyId(discountCard.getDiscountPolicy().getId());
        DiscountCardWithTrademarkDto buildDiscountCardDto = DiscountCardWithTrademarkDto.builder()
                .id(discountCard.getId())
                .name(discountCard.getName())
                .number(discountCard.getNumber())
                .discount(discountCard.getDiscount())
                .isConfirm(discountCard.getIsConfirm())
                .isRent(discountCard.getIsRent())
                .trademark(trademarkByPolicyId)
                .policy(modelMapper.map(discountCard.getDiscountPolicy(), DiscountPolicyDto.class))
                .build();
        if (discountCard.getOwner() != null) {
            buildDiscountCardDto.setOwnerUser(modelMapper.map(discountCard.getOwner(), UserDto.class));
        }
        return buildDiscountCardDto;
    }

    public DiscountCard map(DiscountCardWithTrademarkDto discountCardWithTrademarkDto) {
        DiscountCard builtCard = DiscountCard.builder()
                .name(discountCardWithTrademarkDto.getName())
                .number(discountCardWithTrademarkDto.getNumber())
                .discount(discountCardWithTrademarkDto.getDiscount())
                .isConfirm(discountCardWithTrademarkDto.getIsConfirm())
                .isRent(discountCardWithTrademarkDto.getIsRent())
                .discountPolicy(modelMapper.map(discountCardWithTrademarkDto.getPolicy(), DiscountPolicy.class))
                .build();
        if (discountCardWithTrademarkDto.getId() != null) {
            builtCard.setId(discountCardWithTrademarkDto.getId());
        }
        if (discountCardWithTrademarkDto.getOwnerUser() != null) {
            builtCard.setOwner(modelMapper.map(discountCardWithTrademarkDto.getOwnerUser(), User.class));
        }
        return builtCard;
    }

    public DiscountCard map(DiscountCardForRentDto rentCard){
        DiscountCard builtCard = DiscountCard.builder()
                .name(rentCard.getName())
                .number(rentCard.getNumber())
                .discount(rentCard.getDiscount())
                .isConfirm(rentCard.getIsConfirm())
                .isRent(rentCard.getIsRent())
                .build();
        if (rentCard.getId() != null) {
            builtCard.setId(rentCard.getId());
        }
        if (rentCard.getOwner() != null) {
            builtCard.setOwner(modelMapper.map(rentCard.getOwner(), User.class));
        }
        return builtCard;
    }
}
