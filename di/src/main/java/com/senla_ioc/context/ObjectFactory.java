package com.senla_ioc.context;


import com.senla_ioc.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ObjectFactory {

    private final Map<Class<?>, Object> simpleBeanContext;

    public ObjectFactory() {
        this.simpleBeanContext = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T createBean(Class<T> clazz) {
        if (clazz.getInterfaces().length != 0) {
            try {
                Object c = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
                Class<?> classInterface = Arrays.stream(clazz.getInterfaces())
                        .findFirst()
                        .orElseThrow();
                simpleBeanContext.put(classInterface, c);
                return (T) c;
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        Constructor<?> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(construct -> construct.isAnnotationPresent(Autowired.class))
                .findFirst()
                .orElseThrow();
        Class<?> type = Arrays.stream(constructor.getParameters())
                .findFirst()
                .orElseThrow()
                .getType();
        try {
            if (simpleBeanContext.containsKey(type)) {
                return (T) constructor.newInstance(simpleBeanContext.get(type));
            } else {
                Object c = Class.forName(type.getName()).getDeclaredConstructor().newInstance();
                simpleBeanContext.put(type, c);
                return (T) constructor.newInstance(type);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
