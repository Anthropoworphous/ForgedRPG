package com.github.treesontop.codeGenerator;

public record AccessModifiers(Scope scope, boolean isStatic, boolean isFinal) {
    public AccessModifiers(Scope scope) {
        this(scope, false, false);
    }
    public AccessModifiers(Scope scope, boolean isStatic) {
        this(scope, isStatic, false);
    }

    @Override
    public String toString() {
        return scope().name().toLowerCase() + (isStatic ? " static" : "") + (isFinal ? " final" : "") ;
    }

    public enum Scope {
        PUBLIC,
        PRIVATE,
        PROTECTED;

        public AccessModifiers get() {
            return new AccessModifiers(this);
        }
        public AccessModifiers getStatic() {
            return new AccessModifiers(this, true);
        }
        public AccessModifiers getFinal() {
            return new AccessModifiers(this, false, true);
        }
        public AccessModifiers getStaticFinal() {
            return new AccessModifiers(this, true, true);
        }
    }
}
