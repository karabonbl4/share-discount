package com.senla_ioc.context;

import java.util.Map;

public interface ApplicationContext {

    void buildContext(String basedPackage);
    <T> T getBean(Class<T> clazz);
    Map<Class<?>, Object> getBeans();
}
