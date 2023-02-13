package com.senla.service.impl;

import com.senla.model.Coupon;
import com.senla.repository.impl.CouponRepository;
import com.senla.service.CouponService;
import com.senla.service.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final ModelMapper modelMapper;
    private final CouponRepository couponRepository;
    @Override
    public CouponDto save(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        Coupon coupon1 = couponRepository.saveOrUpdate(coupon);
        return modelMapper.map(coupon1, CouponDto.class);
    }

    @Override
    public CouponDto findById(Long id) {
        Coupon coupon = couponRepository.findById(id);
        return modelMapper.map(coupon, CouponDto.class);
    }
    @Override
    public boolean delete(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponRepository.delete(coupon);
        return couponRepository.findById(couponDto.getId()) == null;
    }

    @Override
    public CouponDto update(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        Coupon coupon1 = couponRepository.saveOrUpdate(coupon);
        return modelMapper.map(coupon1, CouponDto.class);
    }
}
