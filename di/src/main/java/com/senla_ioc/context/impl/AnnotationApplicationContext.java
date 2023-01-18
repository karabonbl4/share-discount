package com.senla_ioc.context.impl;

import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;
import com.senla_ioc.annotation.Value;
import com.senla_ioc.context.ApplicationContext;
import com.senla_ioc.context.ObjectFactory;
import com.senla_ioc.context.PropertyScanner;
import com.senla_ioc.context.Scanner;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationApplicationContext implements ApplicationContext {

    private final Map<Class<?>, Object> context;
    private final ObjectFactory objectFactory;
    private final Scanner classScanner;
    private final PropertyScanner propertyScanner;

    public AnnotationApplicationContext(ObjectFactory objectFactory, Scanner classScanner){
        this.classScanner = classScanner;
        this.objectFactory = objectFactory;
        this.propertyScanner = new PropertyScanner();
        this.context = new HashMap<>();
    }

    @Override
    public void buildContext(String basedPackage) {
        // todo scan package and collect all classes
        // todo find @Component from allClasses
        // todo inject new object to field
        // todo put them to Map context
//        Set<Class<?>> classes = classScanner.scanClasses(basedPackage);

        Reflections reflections = new Reflections(basedPackage);
        Set<Class<?>> typesAnnotatedWithComponent = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> classesWithInterface = typesAnnotatedWithComponent.stream()
                .filter(aClass -> aClass.getInterfaces().length != 0)
                .collect(Collectors.toSet());
        for (Class<?> classWithInterface : classesWithInterface) {
            if (classWithInterface.getInterfaces().length == 1) {
                Class<?> classInterface = Arrays.stream(classWithInterface.getInterfaces())             //while with 1 interface
                        .findFirst().orElseThrow();
                Object bean = objectFactory.createBean(classWithInterface);
                if(hasValueAnnotation(bean)){
                    injectValueToFieldFromProperties(bean);
                }
                context.put(classInterface, bean);
            }
        }
            for (Class<?> clazz : typesAnnotatedWithComponent) {

                if (clazz.getInterfaces().length == 0) {
                    Object bean = objectFactory.createBean(clazz);
                    if(hasValueAnnotation(bean)){
                        injectValueToFieldFromProperties(bean);
                    }
                    context.put(clazz, bean);
                }
            }

    }
    @Override
    public <T> T getBean(Class<T> clazz) {

        return (T) context.get(clazz);
    }

    public Map<Class<?>, Object> getBeans() {

        return this.context;
    }
    private void injectValueToFieldFromProperties(Object object){
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

    private boolean hasValueAnnotation(Object object){
        return Arrays.stream(object.getClass().getDeclaredFields())
                .anyMatch(field -> field.isAnnotationPresent(Value.class));

    }

    private String getValueAnnotationValue(String value){
        if(propertyScanner.getProperties().isEmpty()) {
            propertyScanner.scanProperties("C:\\java\\courses\\probable-octo-potato\\di\\src\\main\\resources\\application.properties");
        }
        return propertyScanner.getProperties().get(value);
    }
}

