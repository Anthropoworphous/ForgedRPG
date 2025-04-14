package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLValue;

public record SQLValuePair(SQLValue value1, SQLValue value2) implements SQLValue {
    @Override
    public String toString() {
        return value1.toString() + " AND " + value2;
    }
}
