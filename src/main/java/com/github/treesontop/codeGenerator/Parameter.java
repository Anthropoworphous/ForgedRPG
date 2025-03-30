package com.github.treesontop.codeGenerator;

public record Parameter(String name, TypeReference type) {
    @Override
    public String toString() {
        if (type instanceof TypeReference.Void) throw new RuntimeException("WTF Is a null param stupid");
        return "%s %s".formatted(type().literal(), name);
    }
}
