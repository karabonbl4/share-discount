package com.senla.service.impl;

import com.senla.model.Coupon;
import com.senla.model.Purchase;
import com.senla.repository.CouponRepository;
import com.senla.service.CouponService;
import com.senla.service.dto.CouponDto;
import com.senla.service.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final ModelMapper modelMapper;
    private final CouponRepository couponRepository;
    @Override
    public void save(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponRepository.save(coupon);
    }

    @Override
    public CouponDto findById(Long id) {
        Coupon coupon = couponRepository.findById(id);
        CouponDto returnCoupon = modelMapper.map(coupon, CouponDto.class);
        return returnCoupon;
    }
    @Override
    public void delete(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponRepository.delete(coupon);
    }

    @Override
    public CouponDto findByPurchase(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        Coupon couponByPurchase = couponRepository.getCouponByPurchase(purchase);
        return modelMapper.map(couponByPurchase, CouponDto.class);
    }

    @Override
    public void update(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponRepository.update(coupon);
    }
}
