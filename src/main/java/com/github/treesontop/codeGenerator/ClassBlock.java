package com.github.treesontop.codeGenerator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ClassBlock extends CodeBlock  {
    private String header;

    public ClassBlock(AccessModifiers access, String name, int indentLevel) {
        super(indentLevel);
        header = "%s class %s {%n".formatted(access.toString(), name);
    }

    public ClassBlock(AccessModifiers access, TypeReference extend, String name, int indentLevel) {
        super(indentLevel);
        header = "%s class %s extends %s {%n".formatted(access.toString(), name, extend.literal());
    }
    public ClassBlock(AccessModifiers access, TypeReference extend, String name, int indentLevel, TypeReference... implement) {
        super(indentLevel);
        var str = Arrays.stream(implement).map(TypeReference::literal).collect(Collectors.joining(", "));
        header = "%s class %s extends %s implements %s {%n".formatted(access.toString(), name, extend.literal(), str);
    }
    public ClassBlock(AccessModifiers access, String name, int indentLevel, TypeReference... implement) {
        super(indentLevel);
        var str = Arrays.stream(implement).map(TypeReference::literal).collect(Collectors.joining(", "));
        header = "%s class %s implements %s {%n".formatted(access.toString(), name, str);
    }


    public CodeBlock append(Field field) {
        return append(field.toString());
    }
    public CodeBlock append(Method method) {
        return append(method.toString());
    }

    @Override
    public String header() {
        return header;
    }
}
