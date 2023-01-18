package com.senla_ioc.context;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Scanner implements ClassFinder {
    private final Set<Class<?>> allClasses;

    private final char PKG_SEPARATOR = '.';
    private final char DIR_SEPARATOR = '/';

    public Scanner() {
        this.allClasses = new HashSet<>();
    }

    @Override
    public Set<Class<?>> scanClasses(String scannedPackage) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        for (File file : Objects.requireNonNull(scannedDir.listFiles())) {
            allClasses.addAll(find(file, scannedPackage));
        }
        return allClasses;
    }

    private Set<Class<?>> find(File file, String scannedPackage) {
        Set<Class<?>> classes = new HashSet<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        String CLASS_FILE_SUFFIX = ".class";
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
}
