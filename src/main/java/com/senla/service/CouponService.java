package com.senla.service;

import com.senla.model.Coupon;
import com.senla.service.dto.CouponDto;

import java.util.List;


public interface CouponService {
    CouponDto save(CouponDto couponDto);
    CouponDto findById(Long id);
    List<CouponDto> findAll();
    boolean delete(CouponDto couponDto);
    CouponDto update(CouponDto couponDto);
}
