package com.github.treesontop;

import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class Util {
    public static Set<Class<?>> getAnnotatedClass(String namespace, Class<? extends Annotation> annotationType) {
    Reflections ref = new Reflections(namespace);
    return ref.getTypesAnnotatedWith(annotationType);
}
}
