package com.senla.repository.impl;

import com.senla.config.ConfigurationClass;
import com.senla.config.TestLiquibaseConfiguration;
import com.senla.config.TestJPAConfig;
import com.senla.model.Coupon;
import com.senla.model.Purchase;
import com.senla.model.Trademark;
import com.senla.repository.CouponRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { TestJPAConfig.class, ConfigurationClass.class, TestLiquibaseConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class CouponRepositoryImplTest {

    @Resource
    private CouponRepository couponRepository;

    @Test
    public void getCouponByPurchase() {
        Trademark trademark = Trademark.builder()
                .id(1L)
                .title("OZ")
                .build();
        Coupon expectCoupon = Coupon.builder()
                .id(1L)
                .name("coupon144")
                .startDate(LocalDate.parse("2011-11-10"))
                .endDate(LocalDate.parse("2011-11-13"))
                .discount(BigDecimal.valueOf(0.53))
                .used(false)
                .trademarkId(trademark)
                .build();

        Purchase purchase = Purchase.builder()
                .name("purchase2")
                .build();

        Coupon actualCouponByPurchase = couponRepository.getCouponByPurchase(purchase);

        assertEquals(expectCoupon, actualCouponByPurchase);

    }
}