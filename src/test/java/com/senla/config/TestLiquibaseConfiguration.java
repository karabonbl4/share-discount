package com.senla.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestLiquibaseConfiguration {
    @Autowired
    private TestJPAConfig connectionHolder;

    @Bean
    public SpringLiquibase liquibase(){
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(connectionHolder.entityManagerFactory().getDataSource());
        liquibase.setChangeLog(connectionHolder.getEnvironment().getProperty("liquibase.changelog"));
        return liquibase;
    }
}
