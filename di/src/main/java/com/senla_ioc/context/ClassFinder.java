package com.senla_ioc.context;

import java.util.Set;

public interface ClassFinder {
    Set<Class<?>> scanClasses(String scannedPackage);
}
