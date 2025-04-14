package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLGroup;
import com.github.treesontop.database.statement.label.SQLValue;

import java.util.function.Predicate;

public class SQLCondition implements SQLValue {
    public final SQLValue target;
    private final Mode mode;
    private final SQLValue value;

    public SQLCondition(SQLValue target, Mode mode, SQLValue value) {
        this.target = target;
        this.mode = mode;
        this.value = value;
    }

    @Override
    public String toString() {
        if (!mode.targetCheck.test(value)) throw new RuntimeException("Failed target check");
        if (!mode.valueCheck.test(value)) throw new RuntimeException("Failed value check");

        return "%s %s %s".formatted(
            target.toString(),
            mode.toString(),
            value instanceof SQLGroup ? "(%s)".formatted(value.toString()) : value.toString()
        );
    }

    public enum Mode {
        EQUAL("="),
        NOT_EQUAL("!="),
        LESS_THAN("<"),
        GREATER_THAN(">"),
        LESS_THAN_OR_EQUAL_TO("<"),
        GREATER_THAN_OR_EQUAL_TO(">"),
        LIKE("LIKE"),
        IN("IN", value -> value instanceof SQLGroup),
        NOT_IN("NOT IN", value -> value instanceof SQLGroup),
        BETWEEN("BETWEEN", value -> value instanceof SQLValuePair);

        private final String sql;
        private final Predicate<SQLValue> targetCheck;
        private final Predicate<SQLValue> valueCheck;

        Mode(Predicate<SQLValue> targetCheck, String sql, Predicate<SQLValue> valueCheck) {
            this.sql = sql;
            this.targetCheck = targetCheck;
            this.valueCheck = valueCheck;
        }
        Mode(String sql) {
            this(ignore -> true, sql, ignore -> true);
        }
        Mode(Predicate<SQLValue> targetCheck, String sql) {
            this(targetCheck, sql, ignore -> true);
        }
        Mode(String sql, Predicate<SQLValue> valueCheck) {
            this(ignore -> true, sql, valueCheck);
        }

        @Override
        public String toString() {
            return sql;
        }
    }
}
