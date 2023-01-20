package com.senla_ioc.context;


import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.context.impl.AnnotationApplicationContext;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class ObjectFactory {

    private final Map<Class<?>, Object> simpleBeanContext;


    public ObjectFactory() {
        this.simpleBeanContext = new HashMap<>();
    }


    public <T> T createBean(Class<T> clazz) {
        if (clazz.getInterfaces().length != 0) {
            return createBeanByInterface(clazz);
        }
        if (Arrays.stream(clazz.getDeclaredConstructors()).anyMatch(constructor -> constructor.isAnnotationPresent(Autowired.class))) {
            return createBeanByConstructorWithAutowiredAnnotation(clazz);
        } else {
            return createBeanByConstructorWithoutAnnotation(clazz);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createBeanByConstructorWithAutowiredAnnotation(Class<?> clazz) {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Constructor<?> constructor = Arrays.stream(declaredConstructors)
                .filter(construct -> construct.isAnnotationPresent(Autowired.class))
                .findFirst()
                .orElseThrow();
        Class<?> type = Arrays.stream(constructor.getParameters())
                .findFirst()
                .orElseThrow()
                .getType();
        try {
            if (!simpleBeanContext.containsKey(type)) {
                Stack<Class<?>> stack = new Stack();
                stack.add(type);
                while (!stack.isEmpty()) {
                    Class<?> lastClass = stack.get(stack.size() - 1);
                    Constructor<?>[] declaredConstructors1 = lastClass.getDeclaredConstructors();
                    Constructor<?> constructor2 = Arrays.stream(declaredConstructors1)
                            .filter(constructor1 -> constructor1.isAnnotationPresent(Autowired.class))
                            .findFirst()
                            .orElseThrow();
                    Class<?> type1 = Arrays.stream(constructor2.getParameters())
                            .findFirst()
                            .orElseThrow()
                            .getType();
                    if (simpleBeanContext.containsKey(type1)) {
                        stack.remove(type);
                        simpleBeanContext.put(lastClass, (T) constructor2.newInstance(simpleBeanContext.get(type1)));
                        Object o = simpleBeanContext.get(type1);
                    } else {
                        stack.add(type1);
                    }
                }
            }
            return (T) constructor.newInstance(simpleBeanContext.get(type));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createBeanByInterface(Class<?> clazz) {
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

    @SuppressWarnings("unchecked")
    private <T> T createBeanByConstructorWithoutAnnotation(Class<?> clazz) {
        try {
            Object c = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
            simpleBeanContext.put(clazz, c);
            return (T) c;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createBeanBySetterWithAnnotation(Class<?> clazz) {
        Method setter = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Autowired.class))
                .findFirst()
                .orElseThrow();
        Class<?> type = Arrays.stream(setter.getParameters())
                .findFirst()
                .orElseThrow()
                .getType();
        Object c;
        try {
            c = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
            setter.invoke(c, simpleBeanContext.get(type));
            return (T) simpleBeanContext.get(type);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

