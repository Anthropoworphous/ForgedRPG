package com.github.treesontop.codeGenerator;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Method extends CodeBlock {
    private final AccessModifiers access;
    private final TypeReference returnType;
    private final String name;
    private final Parameter[] inputs;

    public Method(AccessModifiers access, TypeReference returnType, String name, Parameter... inputs) {
        super(1);
        this.access = access;
        this.returnType = returnType;
        this.name = name;
        this.inputs = inputs;
    }

    @Override
    public String header() {
        return "%s %s %s(%s) {".formatted(access.toString(), returnType.literal(), name, Arrays.stream(inputs)
                .map(Parameter::toString)
                .collect(Collectors.joining(", ")));
    }
}
