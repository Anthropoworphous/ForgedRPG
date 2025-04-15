package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.statement.label.SQLGroup;
import com.github.treesontop.database.statement.label.SQLStatement;
import com.github.treesontop.database.statement.label.SQLValue;
import com.github.treesontop.database.table.Table;

import java.util.*;
import java.util.stream.Collectors;

public class SQLSelect implements SQLStatement, SQLGroup {
    private final Set<SQLValue> values = new HashSet<>();
    private SQLCondition condition = null;
    private SQLOrderBy orderBy = null;
    private SQLGroupBy groupBy = null;
    private int limit = 0, offset = 0;

    private final Set<Table> tables;
    private final Set<SQLTableLink> tableLinks;

    public SQLSelect(SQLValue... values) {
        this(List.of(values));
    }
    public SQLSelect(Collection<SQLValue> values) {
        this.values.addAll(values);
        tables = values.stream()
            .filter(v -> v instanceof Column)
            .map(column -> ((Column) column).table()
                .orElseThrow(() -> new RuntimeException("Column %s not associated with any table"
                    .formatted(((Column) column).name))))
            .collect(Collectors.toSet());
        tableLinks = new HashSet<>();
    }

    public SQLSelect limit(int limit) { this.limit = limit; return this; }

    public SQLSelect condition(SQLCondition condition) { this.condition = condition; return this; }

    public SQLSelect orderBy(SQLOrderBy orderBy) { this.orderBy = orderBy; return this; }

    public SQLSelect linkTable(SQLTableLink link) { tableLinks.add(link); return this; }

    @Override
    public String compile() {
        var sql = "SELECT %s%nFROM %s".formatted(
            values.isEmpty() ? "*" : values.stream().map(SQLValue::toString).collect(Collectors.joining(", ")),
            tables.stream().map(table -> table.name).collect(Collectors.joining(", "))
        );
        if (!tableLinks.isEmpty()) sql += tableLinks.stream()
            .map(SQLTableLink::toString)
            .collect(Collectors.joining("\n"));
        if (condition != null) sql += "\n" + condition;
        if (orderBy != null) {
            if (!orderBy.valueCheck(values)) throw new RuntimeException("Order by value is not in the list");
            sql += "\n" + orderBy.toString();
        }
        if (groupBy != null) {
            if (!groupBy.valueCheck(values)) throw new RuntimeException("Group by value is not in the list");
            sql += "\n" + groupBy.toString();
        }
        if (limit > 0) sql += "\n" + "LIMIT " + limit + (offset > 0 ? " OFFSET " + offset : "");

        return sql + ';';
    }
}
