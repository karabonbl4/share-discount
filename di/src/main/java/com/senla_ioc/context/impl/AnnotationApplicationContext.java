package com.senla_ioc.context.impl;

import com.senla_ioc.annotation.Autowired;
import com.senla_ioc.annotation.Component;
import com.senla_ioc.context.ApplicationContext;
import com.senla_ioc.context.ObjectFactory;
import com.senla_ioc.context.Scanner;
import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

public class AnnotationApplicationContext implements ApplicationContext {

    private final Map<Class<?>, Object> context;
    private final ObjectFactory objectFactory;
    private final Scanner classScanner;

    public AnnotationApplicationContext() {
        this.classScanner = new Scanner();
        this.objectFactory = new ObjectFactory();
        this.context = new HashMap<>();
    }

    public static ApplicationContext run(String applicationPackage) {
        ApplicationContext applicationContext = new AnnotationApplicationContext();
        applicationContext.buildContext(applicationPackage);
        return applicationContext;
    }

    @Override
    public void buildContext(String basedPackage) {
        Set<Class<?>> classes = classScanner.scanClasses(basedPackage);

        Reflections reflections = new Reflections(classes);
        Set<Class<?>> typesAnnotatedWithComponent = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> classesWithInterfaceAndComponentAnnotation = typesAnnotatedWithComponent.stream()
                .filter(aClass -> aClass.getInterfaces().length != 0)
                .collect(Collectors.toSet());
        Set<Class<?>> collect = typesAnnotatedWithComponent.stream().filter(fclass -> Arrays.stream(fclass.getDeclaredConstructors())
                .anyMatch(constructor -> !constructor.isAnnotationPresent(Autowired.class))).collect(Collectors.toSet());

        for (Class<?> clazz : collect) {
            if (clazz.getInterfaces().length == 0) {
                Object bean = objectFactory.createBean(clazz);

                context.put(clazz, bean);
            }
        }
        for (Class<?> classWithInterface : classesWithInterfaceAndComponentAnnotation) {
            if (classWithInterface.getInterfaces().length == 1) {
                objectFactory.putClassByInterface(classWithInterface);
            }
        }

        for (Class<?> classWithInterface : classesWithInterfaceAndComponentAnnotation) {
            if (classWithInterface.getInterfaces().length == 1) {
                Class<?> classInterface = Arrays.stream(classWithInterface.getInterfaces())
                        .findFirst().orElseThrow();
                Object bean = objectFactory.createBean(classWithInterface);
                context.put(classInterface, bean);
            }
        }
        for (Class<?> clazz : typesAnnotatedWithComponent) {
            if (clazz.getInterfaces().length == 0 && !context.containsKey(clazz)) {
                Object bean = objectFactory.createBean(clazz);
                context.put(clazz, bean);
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) context.get(clazz);
    }








}

