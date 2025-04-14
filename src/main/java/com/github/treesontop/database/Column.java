package com.github.treesontop.database;

import com.github.treesontop.database.data.SQLData;
import com.github.treesontop.database.statement.SQLTableLink;
import com.github.treesontop.database.statement.label.SQLValue;
import com.github.treesontop.database.table.Table;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public final class Column implements SQLValue {
    public final String name;
    public final SQLDataType dataType;

    private final Config config;
    private @Nullable Table table;

    public Column(String name, SQLDataType dataType, Config config) {
        this.name = name;
        this.dataType = dataType;
        this.config = config;
        this.table = null;
    }

    public SQLDataType dataType() {
        return dataType;
    }

    public Config config() {
        return config;
    }

    public void table(Table table) { this.table = table; }
    public Optional<Table> table() {
        return Optional.ofNullable(table);
    }

    public Filled fill(SQLData data) {
        return new Filled(this, data);
    }

    public SQLTableLink link(Column other) {
        if (table().isEmpty() || other.table().isEmpty()) {
            throw new RuntimeException("Not all column is associated with a table");
        } else if (Objects.equals(table().get().name, other.table().get().name)) {
            throw new RuntimeException("Can not link columns in the same table");
        }

        return new SQLTableLink(table, other.table, new SQLTableLink.ColumnLink(this, other));
    }

    @Override
    public String toString() {
        return table().map(table -> table.name + '.').orElse("") + name;
    }



    public record Filled(Column column, SQLData data) {
        public Filled {
            if (data.dataType != column.dataType) throw new RuntimeException("Invalid data type");
        }
    }

    public enum Config {
        KEY,
        UNIQUE,
        NULLABLE,
        DEFAULT;

        @Override
        public String toString() {
            if (this == KEY) {
                return "PRIMARY KEY";
            } else if (this == UNIQUE) {
                return "UNIQUE";
            } else if (this == NULLABLE) return "";
            return "NOT NULL";
        }
    }
}
