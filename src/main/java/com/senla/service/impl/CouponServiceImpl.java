package com.senla.service.impl;

import com.senla.repository.impl.CouponRepository;
import com.senla.model.Coupon;
import com.senla.service.CouponService;
import com.senla.service.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final ModelMapper modelMapper;
    private final CouponRepository couponRepository;

    public CouponDto save(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        Coupon coupon1 = couponRepository.saveOrUpdate(coupon);
        return modelMapper.map(coupon1, CouponDto.class);
    }

    public CouponDto findById(Long id) {
        Coupon coupon = couponRepository.findById(id);
        return modelMapper.map(coupon, CouponDto.class);
    }

    public List<CouponDto> findAll() {
        return couponRepository.findAll().stream()
                .map(coupon -> modelMapper.map(coupon, CouponDto.class))
                .collect(Collectors.toList());
    }

    public boolean delete(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponRepository.delete(coupon);
        return couponRepository.findById(couponDto.getId()) == null;
    }

    @Override
    public CouponDto update(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        return modelMapper.map(couponRepository.saveOrUpdate(coupon), CouponDto.class);
    }
}
