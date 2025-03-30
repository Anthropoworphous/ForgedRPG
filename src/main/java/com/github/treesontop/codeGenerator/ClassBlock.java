package com.github.treesontop.codeGenerator;

public class ClassBlock extends CodeBlock {
    private String header;

    public ClassBlock(AccessModifiers access, String name, int indentLevel) {
        super(indentLevel);
        header = "%s class %s {%n".formatted(access.toString(), name);
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
