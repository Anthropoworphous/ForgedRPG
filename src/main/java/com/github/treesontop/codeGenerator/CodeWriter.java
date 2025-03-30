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

    public TypeReference useType(Class<?> c) {
        var t = new TypeReference(c);
        imports.put(c, t.getImport());
        return t;
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
        private final int _indentLevel;
        private final List<String> _lines = new ArrayList<>();

        public CodeBlock(int indentLevel) {
            this._indentLevel = indentLevel;
        }

        @Override
        public String toString() {
            var str = new StringBuilder();

            _lines.forEach(l -> str.append(indent(_indentLevel + 1)).append(l).append("%n"));

            return "%s%s%n%s%s}".formatted(indent(_indentLevel), header(), str, indent(_indentLevel));
        }

        public String header() { return ""; }

        public CodeBlock append(String line) { _lines.add(line); return this; }
        public CodeBlock append(CodeBlock block) {
            var hdr = block.header();
            if (!hdr.isBlank()) { _lines.add(hdr); }
            for (String l : block._lines) {
                String indent = indent(block._indentLevel - _indentLevel);
                _lines.add(indent + l);
            }
            _lines.add("}");
            return this;
        }
    }

    public static class If extends CodeBlock {
        private final String _comparison;

        public If(String comparison, int indentLevel) {
            super(indentLevel);
            _comparison = comparison;
        }

        @Override
        public String header() {
            return "if (%s) {".formatted(_comparison);
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
        private final Assignment _counter;
        private final String _comparison;
        private final String _change;

        public For(Assignment counter, String comparison, String change, int indentLevel) {
            super(indentLevel);
            _counter = counter;
            _comparison = comparison;
            _change = change;
        }

        @Override
        public String header() {
            return "for(%s; %s; %s) {".formatted(_counter, _comparison, _change);
        }
    }
    public static class While extends CodeBlock {
        private final String _comparison;

        public While(String comparison, int indentLevel) {
            super(indentLevel);
            _comparison = comparison;
        }

        @Override
        public String header() {
            return "while(%s) {".formatted(_comparison);
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

    private record TypeReference(Class<?> c) {
        public String literal() {
            var str = c.getSimpleName();
            if (str.isBlank()) {
                throw new RuntimeException("Unable to get name of class " + c);
            }
            return str;
        }

        public String getImport() {
            if (c.getPackageName().equals("java.lang")) return "";
            return String.format("import %s.%s%n", c.getPackageName(), literal());
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

    private static String indent(int level) { return level <= 0 ? "" : "    " + indent(level - 1); }

//
//    public static void main(String[] args) throws FileNotFoundException {
//        var w = new CodeWriter();
//        var e = w.makePrivateMethod("kys", w.useType(String.class),
//                new If("owo == false", 1).append(
//                        new While("false", 2)
//                                .append("kys(owo);")
//                                .append(new For(new Assignment(new Parameter("i", w.useType(Integer.class)), "0"), "< 5", "i++", 3)
//                                        .append("gay(you);"))
//                ), new Parameter("owo", w.useType(Boolean.class)));
//
//        System.out.printf((e.toString()) + "%n");
//        w.imports.values().forEach(System.out::print);
//    }
}
