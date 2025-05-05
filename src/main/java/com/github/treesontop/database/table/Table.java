package com.github.treesontop.database.table;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.generator.MakeColumn;
import com.github.treesontop.database.statement.SQLCreateTable;
import com.github.treesontop.database.statement.SQLInsert;
import com.github.treesontop.database.statement.SQLSelect;

import java.util.*;
import java.util.stream.Collectors;

public class Table {
    private static final Map<String, Table> tables = new HashMap<>();

    public final String name;
    private final Map<String, Column> columns;
    private final Column key;

    protected Table(Builder bdr) {
        name = bdr.name;
        columns = Collections.unmodifiableMap(bdr.columns.stream()
            .collect(Collectors.toMap(column -> column.name, column -> column)));
        key = bdr.key;
    }

    public static Table find(String name) {
        if (!tables.containsKey(name)) throw new RuntimeException("No such table exist");
        return tables.get(name);
    }

    public void register() {
        tables.put(name, this);
    }

    public Column key() { return key; }
    public Set<Column> columns() { return Set.copyOf(columns.values()); }
    public Column column(String name) {
        if (key.name.equals(name)) return key;
        return columns.get(name);
    }

    public boolean checkKey(Column.Filled column) {
        if (!column.column().name.equals(key.name)) return false;
        if (column.column().dataType != key.dataType) return false;
        return column.column().table().orElse(null) == this;
    }


    public SQLCreateTable mkTable() {
        return new SQLCreateTable(this);
    }

    public SQLSelect findExact(Column.Filled keyInput, List<String> columnsSearching) {
        if (!checkKey(keyInput)) throw new RuntimeException("Key invalid");

        return new SQLSelect(columnsSearching.stream().map(columns::get).collect(Collectors.toList())).limit(1);
    }
    public SQLSelect findExact(Column.Filled keyInput, String... columnsSearching) {
        return findExact(keyInput, List.of(columnsSearching));
    }

    public SQLInsert insert() {
        return new SQLInsert(this);
    }

    public static void reset() {
        tables.clear();
    }



    public static class Builder {
        private final String name;
        private final Set<Column> columns = new HashSet<>();
        private Column key = null;

        public Builder(String name) {
            this.name = name;
        }

        public Builder insertColumn(Column column) {
            if (column.config() == Column.Config.KEY) {
                if (key != null) throw new RuntimeException("Attempted to put more than one key");
                key = column;
                return this;
            }
            columns.add(column);
            return this;
        }
        public void insertColumn(MakeColumn annotation) {
            insertColumn(new Column(annotation.name(), annotation.type(), annotation.config()));
        }

        public Table build() {
            if (key == null) throw new RuntimeException("No key was provided");

            var table = new Table(this);
            columns.forEach(column -> column.table(table));
            key.table(table);
            return table;
        }
    }
}
