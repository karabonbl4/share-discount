package com.senla.service.impl;


import com.senla.dao.CouponRepository;
import com.senla.exceptions.NotFoundException;
import com.senla.model.entity.Coupon;
import com.senla.service.CouponService;
import com.senla.model.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final ModelMapper modelMapper;

    private final CouponRepository couponRepository;

    @Override
    public CouponDto save(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        Coupon savedCoupon = couponRepository.save(coupon);
        return modelMapper.map(savedCoupon, CouponDto.class);
    }

    @Override

    public CouponDto findById(Long id) throws NotFoundException {
        Coupon coupon = couponRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(coupon, CouponDto.class);
    }

    @Override
    public List<CouponDto> findAll() {
        return couponRepository.findAll().stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long couponId) {
        Coupon coupon = couponRepository.getReferenceById(couponId);
        couponRepository.delete(coupon);
    }

    @Override
    public CouponDto findByPurchaseId(Long id) {
        Coupon couponByPurchase = couponRepository.getCouponByPurchases_Id(id);
        return modelMapper.map(couponByPurchase, CouponDto.class);
    }

    @Override
    public CouponDto update(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        Coupon returnedCoupon = couponRepository.saveAndFlush(coupon);
        return modelMapper.map(returnedCoupon, CouponDto.class);
    }
}
