package com.github.treesontop.codeGenerator;

import javax.annotation.Nullable;

public record Field(AccessModifiers access, Parameter parameter, @Nullable String initialValue) {
    public Field(AccessModifiers access, Parameter parameter) {
        this(access, parameter, null);
    }

    @Override
    public String toString() {
        return "%s %s%s".formatted(
                access.toString(),
                parameter.toString(),
                initialValue == null ? ";" : " = %s;".formatted(initialValue)
        );
    }
}
