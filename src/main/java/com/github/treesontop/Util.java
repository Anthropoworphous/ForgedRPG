package com.github.treesontop;

import org.reflections.Reflections;

import java.lang.annotation.*;
import java.util.Set;
import java.util.function.Supplier;
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

    public static class Cache<T> {
        private final Supplier<T> getter;
        private T value;

        public Cache(Supplier<T> source) {
            getter = source;
            update();
        }

        public void update() {
            value = getter.get();
        }

        public T value() { return value; }

        public static class UpdateTrigger {
            private final Set<Cache<?>> caches;

            public UpdateTrigger(Set<Cache<?>> caches) {
                this.caches = caches;
            }
            public UpdateTrigger(Cache<?>... caches) {
                this.caches = Set.of(caches);
            }

            public UpdateTrigger join(UpdateTrigger other) {
                caches.addAll(other.caches);
                return this;
            }
            public UpdateTrigger add(Cache<?>... other) {
                caches.addAll(Set.of(other));
                return this;
            }

            public void update() {
                caches.forEach(Cache::update);
            }
        }
    }

    public static record Pair<T1, T2>(T1 v1, T2 v2) {

    }
}
