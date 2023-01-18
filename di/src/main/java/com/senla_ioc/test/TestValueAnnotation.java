package com.senla_ioc.test;

import com.senla_ioc.annotation.Component;
import com.senla_ioc.annotation.Value;

@Component
public class TestValueAnnotation {

    @Value(value = "app.title")
    private String title;

    public TestValueAnnotation() {
    }

    @Override
    public String toString() {
        return "TestValueAnnotation{" +
                "title='" + title + '\'' +
                '}';
    }
}
