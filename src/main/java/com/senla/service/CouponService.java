package com.senla.service;

import com.senla.model.dto.CouponDto;

import java.util.List;


public interface CouponService {
    CouponDto save(CouponDto couponDto);
    CouponDto findById(Long id);
    List<CouponDto> findAll();
    CouponDto update(CouponDto couponDto);
    void delete(Long couponId);
    CouponDto findByPurchaseId(Long id);
}
