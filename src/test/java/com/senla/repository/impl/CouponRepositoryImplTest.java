package com.senla.repository.impl;

import com.senla.Application;
import com.senla.config.TestJPAConfig;
import com.senla.model.Coupon;
import com.senla.model.Purchase;
import com.senla.model.Trademark;
import com.senla.repository.CouponRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {TestJPAConfig.class,
        Application.class},
        loader = AnnotationConfigContextLoader.class)
public class CouponRepositoryImplTest {
    @Autowired
    private CouponRepository couponRepository;

    @Transactional
    @Test
    public void getCouponByPurchase() {
        Coupon expectCoupon = Coupon.builder()
                .id(1L)
                .name("coupon1")
                .startDate(LocalDate.parse("2011-11-11"))
                .endDate(LocalDate.parse("2011-11-12"))
                .discount(BigDecimal.valueOf(0.53))
                .used(false)
                .build();

        Purchase purchase = Purchase.builder()
                .name("purchase2")
                .build();

        Coupon actualCouponByPurchase = couponRepository.getCouponByPurchase(purchase);

        assertEquals(expectCoupon.getId(), actualCouponByPurchase.getId());
        assertEquals(expectCoupon.getName(), actualCouponByPurchase.getName());
        assertEquals(expectCoupon.getStartDate(), actualCouponByPurchase.getStartDate());
        assertEquals(expectCoupon.getEndDate(), actualCouponByPurchase.getEndDate());
        assertEquals(expectCoupon.getDiscount(), actualCouponByPurchase.getDiscount());
        assertEquals(expectCoupon.getUsed(), actualCouponByPurchase.getUsed());
    }

    @Transactional
    @Test
    public void save_coupon() {
        int couponsBeforeAddingElseOne = couponRepository.findAll().size();
        Coupon expectCoupon = Coupon.builder()
                .name("testCoupon")
                .startDate(LocalDate.parse("2020-10-15"))
                .endDate(LocalDate.parse("2020-10-17"))
                .discount(BigDecimal.valueOf(0.51))
                .used(true)
                .trademarkId(Trademark.builder().id(1L).title("OZ").build())
                .build();
        couponRepository.save(expectCoupon);
        int couponsAfterAdding = couponRepository.findAll().size();

        assertEquals(couponsBeforeAddingElseOne+1, couponsAfterAdding);
    }

    @Transactional
    @Test
    public void update_coupon(){
        Coupon actualCouponFromDB = couponRepository.findById(1L);
        String oldName = actualCouponFromDB.getName();

        actualCouponFromDB.setName("someTestName");
        couponRepository.update(actualCouponFromDB);

        String actualCouponName = couponRepository.findById(1L).getName();

        actualCouponFromDB.setName(oldName);
        couponRepository.update(actualCouponFromDB);

        assertEquals("someTestName", actualCouponName);
    }

    @Transactional
    @Test
    public void delete_coupon(){
        int couponsBeforeDeleting = couponRepository.findAll().size();
        Coupon deletingCoupon = Coupon.builder()
                .name("testCoupon")
                .startDate(LocalDate.parse("2020-10-15"))
                .endDate(LocalDate.parse("2020-10-17"))
                .discount(BigDecimal.valueOf(0.51))
                .used(true)
                .trademarkId(Trademark.builder().id(1L).title("OZ").build())
                .build();
        couponRepository.delete(deletingCoupon);
        int couponsAfterRemoving = couponRepository.findAll().size();

        assertEquals(couponsBeforeDeleting, couponsAfterRemoving);
    }
}