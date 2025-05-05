package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.statement.label.SQLStatement;
import com.github.treesontop.database.table.Row;
import com.github.treesontop.database.table.Table;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SQLInsert implements SQLStatement {
    private final Table table;
    private final Set<Row> rows = new HashSet<>();

    private boolean replace = true;

    public SQLInsert(Table table) {
        this.table = table;
        if (table.columns().isEmpty()) throw new RuntimeException("Need at least one column that's not key");
    }

    @CanIgnoreReturnValue
    public SQLInsert replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    @CanIgnoreReturnValue
    public SQLInsert insert(Row... row) {
        rows.addAll(List.of(row));
        return this;
    }
    @CanIgnoreReturnValue
    public SQLInsert insert(Column.Filled key, Column.Filled... values) {
        rows.add(new Row(table, key, values));
        return this;
    }

    @Override
    public String compile() {
        if (rows.isEmpty()) throw new RuntimeException("No row were inserted");
        var randomRow = rows.stream().findAny().get();

        String sql = "%s INTO %s (%s, %s)%n".formatted(
            replace ? "REPLACE" : "INSERT",
            table.name,
            table.key().name,
            Arrays.stream(randomRow.values())
                .map(filled -> filled.column().name)
                .collect(Collectors.joining(", "))
        ) + "VALUES " +
            rows.stream().map(Row::toString).collect(Collectors.joining(",\n"));

        return sql + ';';
    }
}
