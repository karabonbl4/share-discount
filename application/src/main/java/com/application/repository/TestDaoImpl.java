package com.application.repository;

import com.senla_ioc.annotation.Component;
import com.senla_ioc.annotation.Value;

@Component
public class TestDaoImpl implements TestDao{

    @Value("my.param.db")
    private String param;
    @Override
    public void execute() {
        System.out.println(param);
    }
}
