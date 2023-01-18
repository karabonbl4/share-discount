package com.senla_ioc.test;

import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;

@Component
public class SomeClass {

    private final TestInterface testInterface;

    @Autowired
    public SomeClass(TestInterface testInterface) {
        this.testInterface = testInterface;
    }
}
