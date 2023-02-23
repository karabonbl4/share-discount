package com.senla.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(value = {"com.senla.dao"})
@EnableTransactionManagement
@PropertySource({"classpath:test-application.properties"})
public class TestJPAConfig {

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(jdbcUrl);
        return dataSource;
    }
}
