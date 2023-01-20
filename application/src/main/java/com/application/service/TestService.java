package com.application.service;

import com.application.repository.TestDao;
import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;

@Component
public class TestService {

    private TestDao testDao;

    @Autowired
    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }

    public void execute(){
        testDao.execute();
    }
}
