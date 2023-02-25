package com.senla.dao;

import com.senla.config.LiquibaseConfig;
import com.senla.config.TestJPAConfig;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
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
                LiquibaseConfig.class
        },
        loader = AnnotationConfigContextLoader.class)
@Sql(scripts = "classpath:sql/insert_data.sql")
public class DiscountCardRepositoryTest {
    @Autowired
    private DiscountCardRepository repository;

    @AfterAll
    @Sql(scripts = "classpath:sql/delete-all-from-table.sql")
    public void destroyDB() {
    }

    @Test
    @Transactional
    public void findByOwner_Id() {
        String actualCardName = repository.findByOwner_Id(1L).stream()
                .findFirst()
                .orElseThrow()
                .getName();

        String expectCardName = "gold_card";

        assertEquals(expectCardName, actualCardName);
    }
}