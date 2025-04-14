package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLValue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SQLOrderBy {
    private final List<By> orderBy;

    public SQLOrderBy(By... by) {
        orderBy = List.of(by);
    }

    public boolean valueCheck(Collection<SQLValue> values) {
        return values.containsAll(orderBy.stream().map(By::value).collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return "ORDER BY " + orderBy.stream().map(By::toString).collect(Collectors.joining(", "));
    }



    public record By(SQLValue value, Mode mode) {
        @Override
        public String toString() {
            return value.toString() + " " + mode.toString();
        }
    }

    public enum Mode {
        ASCENDING("ASC"),
        DESCENDING("DESC");

        private final String sql;

        Mode(String sql) {
            this.sql = sql;
        }

        @Override
        public String toString() {
            return sql;
        }
    }
}
