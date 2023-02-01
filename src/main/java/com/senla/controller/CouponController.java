package com.senla.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.service.CouponService;

import com.senla.service.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String findById(String id){
        Long l = Long.parseLong(id);
        CouponDto couponDto = couponService.findById(l);
        return objectMapper.writeValueAsString(couponDto);
    }

    @SneakyThrows
    public String create(String newCoupon){
        CouponDto newCouponDto = objectMapper.readValue(newCoupon, CouponDto.class);
        CouponDto saveCoupon = couponService.save(newCouponDto);
        return objectMapper.writeValueAsString(saveCoupon);
    }

    @SneakyThrows
    public boolean delete(String coupon){
        CouponDto couponDto = objectMapper.readValue(coupon, CouponDto.class);
        return couponService.delete(couponDto);
    }

    @SneakyThrows
    public String update(String coupon){
        CouponDto couponDto = objectMapper.readValue(coupon, CouponDto.class);
        CouponDto update = couponService.update(couponDto);
        return objectMapper.writeValueAsString(update);
    }
}
