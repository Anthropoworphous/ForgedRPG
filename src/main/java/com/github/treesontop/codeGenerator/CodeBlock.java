package com.github.treesontop.codeGenerator;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock {
    final int indentLevel;
    final List<String> lines = new ArrayList<>();

    public CodeBlock(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    @Override
    public String toString() {
        var str = new StringBuilder();

        lines.forEach(line -> str.append(indent(indentLevel + 1)).append(line).append("%n"));

        if (header().isBlank()) {
            return str + indent(indentLevel);
        }
        return "%s%s%s%s}".formatted(indent(indentLevel), header(), str, indent(indentLevel));
    }

    public String header() {
        return "";
    }
    public String end() {
        return "}";
    }

    public CodeBlock newLine() {
        lines.add("");
        return this;
    }
    public CodeBlock append(String line) {
        lines.add(line);
        return this;
    }
    public CodeBlock append(String... lines) {
        for (String line : lines) {
            append(line);
        }
        return this;
    }
    public CodeBlock append(CodeBlock block) {
        var header = block.header();
        if (!header.isBlank()) {
            lines.add(header);
        }
        String indent = indent(block.indentLevel - indentLevel);
        for (String line : block.lines) {
            lines.add(indent + line);
        }
        var end = block.end();
        if (!end.isBlank()) {
            lines.add(end);
        }
        return this;
    }


    public static class ArbitraryBlock extends CodeBlock {
        public ArbitraryBlock(int indentLevel) {
            super(indentLevel);
        }

        @Override
        public String end() {
            return "";
        }
    }
    public static class StaticBlock extends CodeBlock {
        public StaticBlock(int indentLevel) {
            super(indentLevel);
        }

        @Override
        public String header() {
            return "static {";
        }
    }
    public static class TextBlock extends CodeBlock {
        public TextBlock(int indentLevel) {
            super(indentLevel);
        }

        @Override
        public String header() {
            return "    \"\"\"";
        }

        @Override
        public String end() {
            return "    \"\"\";";
        }

        @Override
        public String toString() {
            var str = new StringBuilder();

            lines.forEach(line -> str.append(indent(indentLevel + 1)).append(line).append("%n"));

            return "%s%s%s%s".formatted(indent(indentLevel), header(), str, indent(indentLevel));
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
            return "if (%s) {%n".formatted(comparison);
        }

        public static class Else extends CodeBlock {
            public Else(byte indentLevel) {
                super(indentLevel);
            }

            @Override
            public String header() {
                return "else {%n";
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
            return "for(%s; %s; %s) {%n".formatted(counter, comparison, change);
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
            return "while(%s) {%n".formatted(comparison);
        }
    }

    private static String indent(int level) {
        return "    ".repeat(level);
    }
}
