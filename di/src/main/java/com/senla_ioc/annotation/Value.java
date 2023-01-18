package com.senla_ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Value {
    String value();
}
