package com.github.treesontop.codeGenerator;

import java.util.Objects;

public class TypeReference {
    private final Class<?> clazz;

    //Use method in CodeWriter to generate TypeReference
    protected TypeReference(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String literal() {
        var str = clazz.getSimpleName();
        if (str.isBlank()) {
            throw new RuntimeException("Unable to get name of class " + clazz);
        }
        return str;
    }

    public String getImport() {
        if (clazz.getPackageName().equals("java.lang")) return "";
        return String.format("import %s.%s;%n", clazz.getPackageName(), literal());
    }

    @Override
    public String toString() {
        return literal();
    }

    public static Void voidType() {
        return new Void();
    }



    public static class Void extends TypeReference {
        private Void() { super(null); }

        @Override
        public String toString() {
            return "void";
        }

        @Override
        public String literal() {
            throw new RuntimeException("Should not be called on this class");
        }

        @Override
        public String getImport() {
            return "";
        }
    }
}
