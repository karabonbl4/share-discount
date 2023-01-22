package com.application.service;

import com.application.repository.DatabaseInterface;
import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;

@Component
public class TestServiceImpl implements TestService{

    private DatabaseInterface database;

    @Autowired
    public void setDatabase(DatabaseInterface database) {
        this.database = database;
    }

    public String execute(){
        return database.execute();
    }
}
