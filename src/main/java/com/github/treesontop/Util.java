package com.github.treesontop;

import org.reflections.Reflections;

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

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DoNotScan {}
}
