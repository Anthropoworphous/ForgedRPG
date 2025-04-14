package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLValue;

public record SQLStrValue(String value) implements SQLValue {
    @Override
    public String toString() {
        return value;
    }
}
