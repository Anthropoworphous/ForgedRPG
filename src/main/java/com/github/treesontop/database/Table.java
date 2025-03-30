package com.github.treesontop.database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Table {
    public final String name;
    private final Map<String, Column> _columns;
    private final Set<String> _primaryKeys;

    public Table(Builder bdr) {
        name = bdr._name;
        _columns = Collections.unmodifiableMap(bdr._columns);
        _primaryKeys = bdr._columns.entrySet().stream()
                .filter(set -> set.getValue().config.PrimaryKey())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }



    public static class Builder {
        private String _name;
        private final Map<String, Column> _columns;

        public Builder(String name) {
            _columns = new HashMap<>();
        }

        public void insertColumn(String name, Column column) {
            _columns.put(name, column);
        }

        public Table build() {
            return new Table(this);
        }
    }
}
