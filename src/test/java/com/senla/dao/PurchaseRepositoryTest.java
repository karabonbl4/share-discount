package com.senla.dao;

import com.senla.config.LiquibaseConfig;
import com.senla.config.TestJPAConfig;
import com.senla.model.entity.Purchase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {TestJPAConfig.class,
                LiquibaseConfig.class
        },
        loader = AnnotationConfigContextLoader.class)
@Sql(scripts = "classpath:sql/insert_data.sql")
public class PurchaseRepositoryTest {
    
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    @Transactional
    public void findByCard_Id() {
        List<Purchase> actualPurchases = purchaseRepository.findByCard_Id(1L, Pageable.unpaged());

        String expectPurchaseName = "purchase2";
        String actualPurchaseName = actualPurchases.stream()
                .findFirst()
                .orElseThrow()
                .getName();

        assertEquals(expectPurchaseName, actualPurchaseName);
    }

    @Test
    @Transactional
    public void findByUser_Id() {
        List<Purchase> actualPurchases = purchaseRepository.findByBuyer_Id(1L, Pageable.unpaged());

        String actualPurchaseName = actualPurchases.stream()
                .findFirst()
                .orElseThrow()
                .getName();
        String expectPurchaseName = "purchase2";

        assertEquals(expectPurchaseName, actualPurchaseName);
    }
}