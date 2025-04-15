package com.github.treesontop.database.table;

import com.github.treesontop.database.Column;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Row(Table table, Column.Filled key, Column.Filled... values) {
    public boolean validate() {
        return table.checkKey(key) &&
            Arrays.stream(values)
                .map(filled -> filled.column().name)
                .collect(Collectors.toSet())
                .containsAll(table.columns().stream()
                    .map(column -> column.name)
                    .collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return "(%s, %s)".formatted(key.data().sqlForm(), Arrays.stream(values)
            .map(column -> column.data().sqlForm())
            .collect(Collectors.joining(", ")));
    }
}
