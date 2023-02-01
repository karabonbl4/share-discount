package com.senla.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfiguration {

    private final ConnectionHolder connectionHolder;

    @Bean
    public SpringLiquibase liquibase(){
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(connectionHolder.getComboPooledDataSource());
        liquibase.setChangeLog(connectionHolder.getProperties().getProperty("liquibase.changelog"));
        return liquibase;
    }
}
