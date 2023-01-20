package com.senla_ioc.context;


import com.senla_ioc.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class ObjectFactory {

    private final Map<Class<?>, Object> simpleBeanContext;
    private final Stack<Class<?>> stack;


    public ObjectFactory() {
        this.simpleBeanContext = new HashMap<>();
        this.stack = new Stack<>();
    }


    public <T> T createBean(Class<T> clazz) {
        if (clazz.getInterfaces().length != 0) {
            return createBeanByInterface(clazz);
        }
        if (Arrays.stream(clazz.getDeclaredConstructors()).anyMatch(constructor -> constructor.isAnnotationPresent(Autowired.class))) {
            return createBeanByConstructorWithAutowiredAnnotation(clazz);
        } else if (Arrays.stream(clazz.getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(Autowired.class))) {
            return createBeanBySetterWithAutowiredAnnotation(clazz);
        } else if (Arrays.stream(clazz.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(Autowired.class))) {
            return createBeanByFieldWithAutowiredAnnotation(clazz);
        }
        return createBeanByConstructorWithoutAnnotation(clazz);
    }
    private void putBeanToSimpleContext(Class<?> clazz) {
        while (!stack.isEmpty()) {
            if (Arrays.stream(clazz.getDeclaredConstructors()).anyMatch(constructor -> constructor.isAnnotationPresent(Autowired.class))) {
                putBeanToSimpleContextByConstructor(clazz);
            } else if (Arrays.stream(clazz.getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(Autowired.class))) {
                putBeanToSimpleContextBySetter(clazz);
            } else if (Arrays.stream(clazz.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(Autowired.class))) {
                putBeanToSimpleContextByField(clazz);
            }
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
                stack.add(type);
                putBeanToSimpleContext(type);
            }
            return (T) constructor.newInstance(simpleBeanContext.get(type));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createBeanBySetterWithAutowiredAnnotation(Class<?> clazz) {
        Method setter = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Autowired.class))
                .findFirst()
                .orElseThrow();
        Class<?> type = Arrays.stream(setter.getParameters())
                .findFirst()
                .orElseThrow()
                .getType();
        try {
            if (!simpleBeanContext.containsKey(type)) {
                stack.add(type);
                putBeanToSimpleContext(type);
            }
            Object c = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
            setter.invoke(c, simpleBeanContext.get(type));
            return (T) simpleBeanContext.get(type);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createBeanByFieldWithAutowiredAnnotation(Class<?> clazz){
        Field field = Arrays.stream(clazz.getDeclaredFields())
                .filter(autowiringField -> autowiringField.isAnnotationPresent(Autowired.class))
                .findFirst()
                .orElseThrow();
        Class<?> type = field.getType();
        try {
            if (!simpleBeanContext.containsKey(type)) {
                stack.add(type);
                putBeanToSimpleContext(type);
            }
            Object c = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
            field.setAccessible(true);
            field.set(c, simpleBeanContext.get(type));
            simpleBeanContext.put(clazz, c);
            return (T) simpleBeanContext.get(clazz);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private void putBeanToSimpleContextByConstructor(Class<?> type) {
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
            try {
                simpleBeanContext.put(lastClass, constructor2.newInstance(simpleBeanContext.get(type1)));
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            stack.add(type1);
        }
    }

    private void putBeanToSimpleContextBySetter(Class<?> type) {
        Class<?> lastClass = stack.get(stack.size() - 1);
        Method setter = Arrays.stream(type.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Autowired.class))
                .findFirst()
                .orElseThrow();
        Class<?> type1 = Arrays.stream(setter.getParameters())
                .findFirst()
                .orElseThrow()
                .getType();
        if (simpleBeanContext.containsKey(type1)) {
            stack.remove(type);
            try {
                Object c = Class.forName(lastClass.getName()).getDeclaredConstructor().newInstance();
                setter.invoke(c, simpleBeanContext.get(type1));
                simpleBeanContext.put(lastClass, c);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            stack.add(type1);
        }
    }

    private void putBeanToSimpleContextByField(Class<?> type){
        Class<?> lastClass = stack.get(stack.size() - 1);
        Field field = Arrays.stream(type.getDeclaredFields())
                .filter(autowiringField -> autowiringField.isAnnotationPresent(Autowired.class))
                .findFirst()
                .orElseThrow();
        Class<?> type1 = field.getType();
        if (simpleBeanContext.containsKey(type1)) {
            stack.remove(type);
            try {
                Object c = Class.forName(lastClass.getName()).getDeclaredConstructor().newInstance();
                field.setAccessible(true);
                field.set(c, simpleBeanContext.get(type));
                simpleBeanContext.put(lastClass, c);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            stack.add(type1);
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
}

