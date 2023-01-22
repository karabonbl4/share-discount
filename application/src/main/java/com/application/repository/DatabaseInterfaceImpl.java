package com.application.repository;

import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;

@Component
public class DatabaseInterfaceImpl implements DatabaseInterface {

    @Autowired
    private ParametersHolder parametersHolder;

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }
}
