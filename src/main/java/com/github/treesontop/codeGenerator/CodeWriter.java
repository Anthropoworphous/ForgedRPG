package com.github.treesontop.codeGenerator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

public class CodeWriter extends PrintWriter {
    private final Map<Class<?>, String> imports = new HashMap<>(); //Don't print new line, it's included

    public CodeWriter(@NotNull Writer out) {
        super(out);
    }

    public TypeReference useType(Class<?> clazz) {
        var typeReference = new TypeReference(clazz);
        imports.put(clazz, typeReference.getImport());
        return typeReference;
    }

    public Method makePublicStaticVoidMethod(String name, CodeBlock content, Parameter... methodInputs) {
        return new Method("public static void " + name, methodInputs, content);
    }

    public Method makePublicStaticMethod(String name, TypeReference returnType, CodeBlock content, Parameter... methodInputs) {
        return new Method("public static %s %s".formatted(returnType, name), methodInputs, content);
    }

    public Method makePrivateStaticVoidMethod(String name, CodeBlock content, Parameter... methodInputs) {
        return new Method("private static void " + name, methodInputs, content);
    }

    public Method makePrivateStaticMethod(String name, TypeReference returnType, CodeBlock content, Parameter... methodInputs) {
        return new Method("private static %s %s".formatted(returnType, name), methodInputs, content);
    }

    public Method makePublicVoidMethod(String name, CodeBlock content, Parameter... methodInputs) {
        return new Method("public void " + name, methodInputs, content);
    }

    public Method makePublicMethod(String name, TypeReference returnType, CodeBlock content, Parameter... methodInputs) {
        return new Method("public %s %s".formatted(returnType, name), methodInputs, content);
    }

    public Method makePrivateVoidMethod(String name, CodeBlock content, Parameter... methodInputs) {
        return new Method("private void " + name, methodInputs, content);
    }

    public Method makePrivateMethod(String name, TypeReference returnType, CodeBlock content, Parameter... methodInputs) {
        return new Method("private %s %s".formatted(returnType, name), methodInputs, content);
    }

    public static class CodeBlock {
        private final int indentLevel;
        private final List<String> lines = new ArrayList<>();

        public CodeBlock(int indentLevel) {
            this.indentLevel = indentLevel;
        }

        @Override
        public String toString() {
            var str = new StringBuilder();

            lines.forEach(line -> str.append(indent(indentLevel + 1)).append(line).append("%n"));

            return "%s%s%n%s%s}".formatted(indent(indentLevel), header(), str, indent(indentLevel));
        }

        public String header() {
            return "";
        }

        public CodeBlock append(String line) {
            lines.add(line);
            return this;
        }

        public CodeBlock append(CodeBlock block) {
            var header = block.header();
            if (!header.isBlank()) {
                lines.add(header);
            }
            for (String line : block.lines) {
                String indent = indent(block.indentLevel - indentLevel);
                lines.add(indent + line);
            }
            lines.add("}");
            return this;
        }
    }

    public static class If extends CodeBlock {
        private final String comparison;

        public If(String comparison, int indentLevel) {
            super(indentLevel);
            this.comparison = comparison;
        }

        @Override
        public String header() {
            return "if (%s) {".formatted(comparison);
        }

        public static class Else extends CodeBlock {
            public Else(byte indentLevel) {
                super(indentLevel);
            }

            @Override
            public String header() {
                return "else {";
            }
        }

        public static class ElseIf extends If {
            public ElseIf(String comparison, byte indentLevel) {
                super(comparison, indentLevel);
            }

            @Override
            public String header() {
                return "else " + super.header();
            }
        }
    }

    public static class For extends CodeBlock {
        private final Assignment counter;
        private final String comparison;
        private final String change;

        public For(Assignment counter, String comparison, String change, int indentLevel) {
            super(indentLevel);
            this.counter = counter;
            this.comparison = comparison;
            this.change = change;
        }

        @Override
        public String header() {
            return "for(%s; %s; %s) {".formatted(counter, comparison, change);
        }
    }

    public static class While extends CodeBlock {
        private final String comparison;

        public While(String comparison, int indentLevel) {
            super(indentLevel);
            this.comparison = comparison;
        }

        @Override
        public String header() {
            return "while(%s) {".formatted(comparison);
        }
    }

    public record Parameter(String name, TypeReference type) {
        @Override
        public String toString() {
            return "%s %s".formatted(type().literal(), name);
        }
    }

    public record Assignment(Parameter param, String statement) {
        @Override
        public String toString() {
            return param.toString() + " = " + statement;
        }
    }

    private record TypeReference(Class<?> clazz) {
        public String literal() {
            var str = clazz.getSimpleName();
            if (str.isBlank()) {
                throw new RuntimeException("Unable to get name of class " + clazz);
            }
            return str;
        }

        public String getImport() {
            if (clazz.getPackageName().equals("java.lang")) return "";
            return String.format("import %s.%s%n", clazz.getPackageName(), literal());
        }

        @Override
        public String toString() {
            return literal();
        }
    }

    public record Method(String header, Parameter[] inputs, CodeBlock content) {
        public String firstLine() {
            return "%s(%s) {".formatted(header, Arrays.stream(inputs)
                    .map(Parameter::toString)
                    .collect(Collectors.joining(", ")));
        }

        @Override
        public String toString() {
            return firstLine() + "%n" + content.toString() + "%n}";
        }

        public void write(PrintWriter out) {
            out.write(toString());
        }
    }

    private static String indent(int level) {
        return level <= 0 ? "" : "    " + indent(level - 1);
    }
}
