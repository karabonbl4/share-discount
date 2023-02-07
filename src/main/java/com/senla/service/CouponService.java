package com.senla.service;

import com.senla.service.dto.CouponDto;
import com.senla.service.dto.PurchaseDto;


public interface CouponService {
    void save(CouponDto couponDto);
    CouponDto findById(Long id);
    void update(CouponDto couponDto);
    void delete(CouponDto couponDto);
    CouponDto findByPurchase(PurchaseDto purchase);
}
