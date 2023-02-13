package com.senla.service;

import com.senla.model.entity.Purchase;
import com.senla.model.dto.CouponDto;

import java.util.List;


public interface CouponService {
    void save(CouponDto couponDto);
    CouponDto findById(Long id);
    List<CouponDto> findAll();
    void update(CouponDto couponDto);
    void delete(Long couponId);
    CouponDto findByPurchaseId(Long id);
}
