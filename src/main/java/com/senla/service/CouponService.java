package com.senla.service;

import com.senla.service.dto.CouponDto;


public interface CouponService {
    CouponDto save(CouponDto couponDto);
    CouponDto findById(Long id);
    CouponDto update(CouponDto couponDto);
    boolean delete(CouponDto couponDto);
}
