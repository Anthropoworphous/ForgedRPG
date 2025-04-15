package com.github.treesontop.database.table;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.generator.MakeColumn;
import com.github.treesontop.database.statement.SQLSelect;

import java.util.*;
import java.util.stream.Collectors;

public class Table {
    private static final Map<String, Table> tables = new HashMap<>();

    public final String name;
    private final Map<String, Column> columns;
    private final Set<String> primaryKeys;

    protected Table(Builder bdr) {
        name = bdr.name;
        columns = Collections.unmodifiableMap(bdr.columns.stream()
            .collect(Collectors.toMap(column -> column.name, column -> column)));
        primaryKeys = bdr.columns.stream()
                .filter(column -> column.config() == Column.Config.KEY)
                .map(column -> column.name)
                .collect(Collectors.toSet());
    }

    public static Table find(String name) {
        if (!tables.containsKey(name)) throw new RuntimeException("No such table exist");
        return tables.get(name);
    }

    public void register() {
        tables.put(name, this);
    }

    public Collection<Column> columns() { return columns.values(); }
    public Column column(String name) {
        return columns.get(name);
    }

    public SQLSelect findExact(Set<Column.Filled> keys, List<String> columnsSearching) {
        if (!keys.stream().map(key -> key.column().name).collect(Collectors.toSet()).containsAll(primaryKeys)) {
            throw new RuntimeException("Not all keys are provided");
        }

        return new SQLSelect(columnsSearching.stream().map(columns::get).collect(Collectors.toList())).limit(1);
    }
    public SQLSelect findExact(Set<Column.Filled> keys, String... columnsSearching) {
        return findExact(keys, List.of(columnsSearching));
    }



    public static class Builder {
        private final String name;
        private final Set<Column> columns = new HashSet<>();

        public Builder(String name) {
            this.name = name;
        }

        public void insertColumn(Column column) {
            columns.add(column);
        }
        public void insertColumn(MakeColumn annotation) {
            columns.add(new Column(annotation.name(), annotation.type(), annotation.config()));
        }

        public Table build() {
            var table = new Table(this);
            columns.forEach(column -> column.table(table));
            return table;
        }
    }
}
