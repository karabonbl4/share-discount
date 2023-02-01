package com.application.repository;

import com.senla_ioc.annotation.Component;
import com.senla_ioc.annotation.Value;

@Component
public class ParametersHolder {

    @Value("my.param.db")
    private String someText;

    public String getSomeText() {
        return someText;
    }
}
