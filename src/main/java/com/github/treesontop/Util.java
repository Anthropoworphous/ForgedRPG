package com.github.treesontop;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class Util {
    public static Set<Class<?>> getAnnotatedClass(String namespace, Class<? extends Annotation> annotationType) {
    Reflections ref = new Reflections(namespace);
    return ref.getTypesAnnotatedWith(annotationType);
}
}
