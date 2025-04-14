package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLValue;

public record SQLRenamed(SQLValue source, String newName) implements SQLValue {
    @Override
    public String toString() {
        return source.toString() + " AS " + newName;
    }
}
