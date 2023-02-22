package com.senla.dao;

import com.senla.config.TestJPAConfig;
import com.senla.config.TestLiquibaseConfiguration;
import com.senla.model.entity.Coupon;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
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
@Sql(scripts = "classpath:sql/insert_data.sql")
public class CouponRepositoryTest {
    @Autowired
    private CouponRepository repository;

    @Test
    @Transactional
    public void getCouponByPurchase_Id() {
        Coupon couponByPurchase_id = repository.getCouponByPurchase_Id(1L);
        String actualName = couponByPurchase_id.getName();

        String expectName = "coupon1";

        assertEquals(expectName, actualName);

    }
}