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

    public AnnotationApplicationContext(ObjectFactory objectFactory, Scanner classScanner){
        this.classScanner = classScanner;
        this.objectFactory = objectFactory;
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
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class);
        Set<Class<?>> classesWithInterface = typesAnnotatedWith.stream()
                .filter(aClass -> aClass.getInterfaces().length != 0)
                .collect(Collectors.toSet());
        for (Class<?> classWithInterface : classesWithInterface) {
            if (classWithInterface.getInterfaces().length == 1) {
                Class<?> classInterface = Arrays.stream(classWithInterface.getInterfaces())             //while with 1 interface
                        .findFirst().orElseThrow();
                Object bean = objectFactory.createBean(classWithInterface);
                context.put(classInterface, bean);
            }
        }
            for (Class<?> clazz : typesAnnotatedWith) {

                if (clazz.getInterfaces().length == 0) {
                    Object bean = objectFactory.createBean(clazz);
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
}

