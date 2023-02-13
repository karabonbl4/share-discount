package com.senla.dao;

import com.senla.config.TestJPAConfig;
import com.senla.config.TestLiquibaseConfiguration;
import com.senla.model.entity.Coupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {TestJPAConfig.class,
                TestLiquibaseConfiguration.class
        },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class CouponRepositoryTest {
    @Autowired
    private CouponRepository repository;

    @Test
    public void getCouponByPurchases_Id() {
        Coupon couponByPurchases_id = repository.getCouponByPurchases_Id(1L);
        String actualName = couponByPurchases_id.getName();

        String expectName = "coupon1";

        assertEquals(expectName, actualName);

    }
}