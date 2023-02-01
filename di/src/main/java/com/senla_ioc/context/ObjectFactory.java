package com.senla_ioc.context;


import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Value;

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
    private final Map<Class<?>, Class<?>> interfaceClassContext;
    private final PropertyScanner propertyScanner;


    public ObjectFactory() {
        this.simpleBeanContext = new HashMap<>();
        this.stack = new Stack<>();
        this.interfaceClassContext = new HashMap<>();
        this.propertyScanner = new PropertyScanner();
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
    private void putBeanToSimpleContext() {
        while (!stack.isEmpty()) {
            Class<?> clazz = stack.get(stack.size() - 1);//почему-то закидывает предыдущий класс????
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
            if (!simpleBeanContext.containsKey(interfaceClassContext.get(type))) {
                stack.add(interfaceClassContext.get(type));
                putBeanToSimpleContext();
            }
            Class<?> aClass = interfaceClassContext.get(type);
            Object bean = simpleBeanContext.get(aClass);
            if(hasValueAnnotation(bean)){
                injectValueToFieldFromProperties(bean);
            }
            return (T) constructor.newInstance(simpleBeanContext.get(aClass));
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
                putBeanToSimpleContext();
            }
            Object bean = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
            setter.invoke(bean, simpleBeanContext.get(type));
            if(hasValueAnnotation(bean)){
                injectValueToFieldFromProperties(bean);
            }
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
                putBeanToSimpleContext();
            }
            Object bean = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
            field.setAccessible(true);
            field.set(bean, simpleBeanContext.get(type));
            simpleBeanContext.put(clazz, bean);
            if(hasValueAnnotation(bean)){
                injectValueToFieldFromProperties(bean);
            }
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
            putBeanToSimpleContext();
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
        if (simpleBeanContext.containsKey(interfaceClassContext.get(type1))) {
            stack.remove(type);
            try {
                Object c = Class.forName(lastClass.getName()).getDeclaredConstructor().newInstance();
                Class<?> aClass = interfaceClassContext.get(type1);
                setter.invoke(c, simpleBeanContext.get(aClass));
                simpleBeanContext.put(lastClass, c);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            Class<?> aClass = interfaceClassContext.get(type1);
            stack.add(aClass);
            putBeanToSimpleContext();
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
                field.set(c, simpleBeanContext.get(type1));
                simpleBeanContext.put(lastClass, c);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            stack.add(type1);
            putBeanToSimpleContext();
        }
    }
    public void putClassByInterface(Class<?> clazz){
        Class<?> aClass = Arrays.stream(clazz.getInterfaces()).findFirst().orElseThrow();
        interfaceClassContext.put(aClass, clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> T createBeanByInterface(Class<?> clazz) {
        if (!simpleBeanContext.containsKey(clazz)){
            stack.add(clazz);
            putBeanToSimpleContext();
        }
        Object bean = simpleBeanContext.get(clazz);
        if(hasValueAnnotation(bean)){
            injectValueToFieldFromProperties(bean);
        }
        return (T) simpleBeanContext.get(clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> T createBeanByConstructorWithoutAnnotation(Class<?> clazz) {
        try {
            Object bean = Class.forName(clazz.getName()).getDeclaredConstructor().newInstance();
            if(hasValueAnnotation(bean)){
                injectValueToFieldFromProperties(bean);
            }
            simpleBeanContext.put(clazz, bean);
            return (T) simpleBeanContext.get(clazz);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean hasValueAnnotation(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .anyMatch(field -> field.isAnnotationPresent(Value.class));

    }
    private String getValueAnnotationValue(String value) {
        if (propertyScanner.getProperties().isEmpty()) {
            propertyScanner.scanProperties("C:\\java\\courses\\probable-octo-potato\\application\\src\\main\\resources\\application.properties");
        }
        return propertyScanner.getProperties().get(value);
    }
    private void injectValueToFieldFromProperties(Object object) {
        Field fieldWithAnnotation = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Value.class))
                .findFirst()
                .orElseThrow();
        Value valueAnnotation = fieldWithAnnotation.getAnnotation(Value.class);
        String value = valueAnnotation.value();
        String valueFromProperty = getValueAnnotationValue(value);
        fieldWithAnnotation.setAccessible(true);
        try {
            fieldWithAnnotation.set(object, valueFromProperty);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}

