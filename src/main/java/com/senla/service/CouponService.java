package com.senla.service;

import com.senla.model.dto.CouponDto;

import java.util.List;


public interface CouponService {
    CouponDto save(CouponDto couponDto);

    CouponDto findById(Long id);

    List<CouponDto> findAllWithSort(Integer pageNo, Integer pageSize, String[] sort);

    CouponDto update(CouponDto couponDto);

    void delete(Long couponId);

    CouponDto findByPurchaseId(Long id);

    void deactivate(Long id);

    void reserve(Long couponId);

    List<CouponDto> findByUser(Integer pageNo, Integer pageSize, String[] sort);
}
