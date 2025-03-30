package com.github.treesontop.codeGenerator;

public record Assignment(Parameter param, String statement) {
    @Override
    public String toString() {
        return param.toString() + " = " + statement;
    }
}
