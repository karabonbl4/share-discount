package com.senla.repository.impl;

import com.senla.Application;
import com.senla.config.ConfigurationClass;
import com.senla.config.TestLiquibaseConfiguration;
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
        classes = {TestJPAConfig.class, ConfigurationClass.class, TestLiquibaseConfiguration.class, Application.class},
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
                .discount(BigDecimal.valueOf(0.51))
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
    public void save() {
        Coupon coupon = Coupon.builder()
                .id(99999L)
                .name("testCoupon")
                .build();
        couponRepository.save(coupon);
        Coupon actualCoupon = couponRepository.findById(99999L);

        assertNotNull(actualCoupon);
    }
}