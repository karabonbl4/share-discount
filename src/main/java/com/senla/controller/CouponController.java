package com.senla.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.service.CouponService;
import com.senla.service.dto.CouponDto;
import com.senla.service.dto.PurchaseDto;
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
    public void save(String newCoupon){
        CouponDto newCouponDto = objectMapper.readValue(newCoupon, CouponDto.class);
        couponService.save(newCouponDto);
    }

    @SneakyThrows
    public void delete(String coupon){
        CouponDto couponDto = objectMapper.readValue(coupon, CouponDto.class);
    }

    @SneakyThrows
    public void update(String coupon){
        CouponDto couponDto = objectMapper.readValue(coupon, CouponDto.class);
        couponService.update(couponDto);
    }

    @SneakyThrows
    public String getCouponByPurchase(String purchase){
        PurchaseDto purchaseDto = objectMapper.readValue(purchase, PurchaseDto.class);
        CouponDto byPurchase = couponService.findByPurchase(purchaseDto);
        return objectMapper.writeValueAsString(byPurchase);
    }
}
