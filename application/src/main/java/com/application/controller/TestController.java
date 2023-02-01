package com.application.controller;

import com.application.service.TestService;
import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;

@Component
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }


    public String execute() {
        return testService.execute();
    }
}
