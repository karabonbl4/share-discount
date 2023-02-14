package com.senla.service.impl;

import com.senla.dao.CouponRepository;
import com.senla.model.dto.CouponDto;
import com.senla.model.entity.Coupon;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CouponServiceImpl couponService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private static final Long ID = 1L;

    @Test
    public void shouldSaveCouponSuccessfully() {
        final CouponDto couponDto = mock(CouponDto.class);
        final Coupon mapedCoupon = mock(Coupon.class);
        when(modelMapper.map(couponDto, Coupon.class)).thenReturn(mapedCoupon);
        couponService.save(couponDto);

        verify(couponRepository).save(mapedCoupon);
    }

    @Test
    public void shouldFindByIdSuccessfully() {
        final CouponDto couponDto = mock(CouponDto.class);
        final Coupon mapedCoupon = mock(Coupon.class);
        when(modelMapper.map(couponDto, Coupon.class)).thenReturn(mapedCoupon);
        when(couponRepository.findById(ID)).thenReturn(Optional.ofNullable(mapedCoupon));
        when(modelMapper.map(mapedCoupon, CouponDto.class)).thenReturn(couponDto);

        final CouponDto actual = couponService.findById(ID);

        assertNotNull(actual);
        assertEquals(couponDto, actual);
        verify(couponRepository).findById(ID);
    }

    @Test
    public void shouldFindAllSuccessfully() {
        couponService.findAll();

        verify(couponRepository).findAll();
    }

    @Test
    public void shouldDeleteCouponSuccessfully() {
        final Coupon mapedCoupon = mock(Coupon.class);
        when(couponRepository.getReferenceById(ID)).thenReturn(mapedCoupon);

        couponService.delete(ID);

        verify(couponRepository).delete(mapedCoupon);
    }

    @Test
    public void shouldFindByPurchaseIdSuccessfully() {
        final CouponDto couponDto = mock(CouponDto.class);
        final Coupon mapedCoupon = mock(Coupon.class);
        when(couponRepository.getCouponByPurchases_Id(ID)).thenReturn(mapedCoupon);
        when(modelMapper.map(mapedCoupon, CouponDto.class)).thenReturn(couponDto);

        final CouponDto actual = couponService.findByPurchaseId(ID);

        assertNotNull(actual);
        assertEquals(couponDto, actual);
        verify(couponRepository).getCouponByPurchases_Id(ID);
    }

    @Test
    public void chouldUpdateCouponSuccessfully() {
        final CouponDto couponDto = mock(CouponDto.class);
        final Coupon mapedCoupon = mock(Coupon.class);
        when(modelMapper.map(couponDto, Coupon.class)).thenReturn(mapedCoupon);
        when(couponRepository.saveAndFlush(mapedCoupon)).thenReturn(mapedCoupon);
        when(modelMapper.map(mapedCoupon, CouponDto.class)).thenReturn(couponDto);

        CouponDto actualCoupon = couponService.update(couponDto);

        assertNotNull(actualCoupon);
        assertEquals(couponDto, actualCoupon);
        verify(couponRepository).saveAndFlush(mapedCoupon);

    }
}