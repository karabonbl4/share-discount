package com.senla.model.dto.mapper;

import com.senla.model.dto.save.CouponForSaveDto;
import com.senla.model.entity.Coupon;
import com.senla.model.entity.Trademark;
import com.senla.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCouponMapper {

    private final ModelMapper modelMapper;

    public Coupon convertFromSaveDto(CouponForSaveDto couponForSaveDto){
        return Coupon.builder()
                .id(couponForSaveDto.getId())
                .name(couponForSaveDto.getName())
                .startDate(couponForSaveDto.getStartDate())
                .endDate(couponForSaveDto.getEndDate())
                .discount(couponForSaveDto.getDiscount())
                .isUsed(couponForSaveDto.getIsUsed())
                .trademark(modelMapper.map(couponForSaveDto.getTrademark(), Trademark.class))
                .user(modelMapper.map(couponForSaveDto.getUser(), User.class))
                .build();
    }
}
