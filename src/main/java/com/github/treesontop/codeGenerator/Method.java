package com.github.treesontop.codeGenerator;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Method extends CodeBlock {
    private final String header;
    private boolean isOverride = false;

    public Method(AccessModifiers access, TypeReference returnType, String name, Parameter... inputs) {
        super(1);
        header = "%s %s %s(%s) {".formatted(access.toString(), returnType.literal(), name, Arrays.stream(inputs)
            .map(Parameter::toString)
            .collect(Collectors.joining(", ")));
    }

    public void isOverride() {
        isOverride = true;
    }

    @Override
    public String header() {
        return isOverride ? "@Override%n%s%s".formatted("    ".repeat(indentLevel), header) : header;
    }
}
