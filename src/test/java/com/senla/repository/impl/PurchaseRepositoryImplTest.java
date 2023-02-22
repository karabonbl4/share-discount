package com.senla.repository.impl;

import com.senla.Application;
import com.senla.config.TestJPAConfig;
import com.senla.model.Purchase;
import com.senla.model.User;
import com.senla.repository.PurchaseRepository;
import com.senla.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {TestJPAConfig.class,
                Application.class},
        loader = AnnotationConfigContextLoader.class)
public class PurchaseRepositoryImplTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void findByUserId() {
        String actualName = purchaseRepository.findByUserId(1l).getName();

        assertEquals("purchase2", actualName);
    }
}