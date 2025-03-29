package com.github.treesontop.database;

import java.util.*;
import java.util.stream.Collectors;

public class Table {
    public final String name;
    private final Map<String, Column> _columns;
    private final Set<String> _primaryKeys;

    public Table(TableBuilder bdr) {
        name = bdr._name;
        _columns = Collections.unmodifiableMap(bdr._columns);
        _primaryKeys = bdr._columns.entrySet().stream()
                .filter(set -> set.getValue().config.PrimaryKey())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }



    public static class TableBuilder {
        private String _name;
        private final Map<String, Column> _columns;

        public TableBuilder(String name) {
            _columns = new HashMap<>();
        }

        public void InsertColumn(String name, Column column) {
            _columns.put(name, column);
        }

        public Table build() {
            return new Table(this);
        }
    }
}
