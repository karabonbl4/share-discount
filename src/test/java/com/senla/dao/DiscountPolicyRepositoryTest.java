package com.senla.dao;

import com.senla.config.TestJPAConfig;
import com.senla.config.TestLiquibaseConfiguration;
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
public class DiscountPolicyRepositoryTest {

    @Autowired
    private DiscountPolicyRepository repository;

    @BeforeEach
    @Sql(scripts = "classpath:sql/insert_data.sql")
    public void setupDB() {
    }

    @AfterEach
    @Sql(scripts = "classpath:sql/delete-all-from-table.sql")
    public void destroyDB() {
    }

    @Test
    @Transactional
    public void findByTrademark_Id() {
        String actualPolicyName = repository.findByTrademark_Id(1L).stream()
                .findFirst()
                .orElseThrow()
                .getTitle();
        String expectPolicyName = "OZpolicy";

        assertEquals(expectPolicyName, actualPolicyName);
    }
}