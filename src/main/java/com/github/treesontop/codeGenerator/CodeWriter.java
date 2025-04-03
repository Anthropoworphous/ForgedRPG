package com.github.treesontop.codeGenerator;

import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CodeWriter extends PrintWriter {
    private final Map<Class<?>, String> imports = new HashMap<>(); //Don't print new line, it's included
    private final String pkg;

    public CodeWriter(@NotNull Writer out, String pkg) {
        super(out);
        this.pkg = pkg;
    }

    public CodeWriter(ProcessingEnvironment env, String pkg, String className) throws IOException {
        super(env.getFiler().createSourceFile("%s.%s".formatted(pkg, className)).openWriter());
        this.pkg = pkg;
    }

    public TypeReference useType(Class<?> clazz) {
        var typeReference = new TypeReference(clazz);
        imports.put(clazz, typeReference.getImport());
        return typeReference;
    }
    public TypeReference useType(Class<?> clazz, TypeReference... typeParam) {
        var typeReference = new TypeReference.WithTypeParam(clazz, typeParam);
        imports.put(clazz, typeReference.getImport());
        return typeReference;
    }

    public void printPackage() {
        printf("package %s;%n%n", pkg);
    }

    public void printImport() {
        imports.values().forEach(this::printf);
        println();
    }
}
