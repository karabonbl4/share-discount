package com.application.controller;

import com.application.service.TestService;
import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;

@Component
public class TestController {

    @Autowired
    private TestService testService;

    public void execute() {
        testService.execute();
    }
}
