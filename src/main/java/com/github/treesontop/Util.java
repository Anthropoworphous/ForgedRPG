package com.github.treesontop;

import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.Scanners;

import java.lang.annotation.*;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {
    public static Set<Class<?>> getAnnotatedClass(String namespace, Class<? extends Annotation> annotationType) {
        Reflections ref = new Reflections(namespace);
        return ref.getTypesAnnotatedWith(annotationType).stream()
            .filter(c -> !c.isAnnotationPresent(DoNotScan.class))
            .collect(Collectors.toSet());
    }

    public static String capFirst(String str) {
        if (str.length() <= 1) throw new RuntimeException(str + " is too short!");
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DoNotScan {}
}
