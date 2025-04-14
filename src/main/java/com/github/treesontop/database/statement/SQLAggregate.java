package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLValue;

public record SQLAggregate(SQLValue value, Mode mode, boolean distinct) implements SQLValue {
    public SQLAggregate(Mode mode, SQLValue value) {
        this(value, mode, false);
    }

    @Override
    public String toString() {
        return "%s(%s%s)".formatted(mode.name(), distinct ? "DISTINCT " : "", value.toString());
    }

    public enum Mode {
        AVG,
        COUNT,
        MAX,
        MIN,
        SUM
    }
}
