package com.senla_ioc.test.testio;

import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;
import com.senla_ioc.test.TestInterface;

@Component
public class SomeClass {

    private final TestInterface testInterface;


    @Autowired
    public SomeClass(TestInterface testInterface) {
        this.testInterface = testInterface;
    }
}
