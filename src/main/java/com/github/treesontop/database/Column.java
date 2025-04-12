package com.github.treesontop.database;

import com.github.treesontop.database.data.SQLData;

public class Column {
    public final String table;
    public final SQLDataType dataType;
    public final Config config;

    public Column(String table, SQLDataType dataType) {
        this.table = table;
        this.dataType = dataType;
        config = Config.getDefault();
    }
    public Column(String table, SQLDataType dataType, Config config) {
        this.table = table;
        this.dataType = dataType;
        this.config = config;
    }



    public static class Filled {
        public final Column column;
        public final SQLData data;

        public Filled(Column column, SQLData data) {
            if (data.dataType != column.dataType) throw new RuntimeException("Invalid data type");
            this.column = column;
            this.data = data;
        }
    }

    public record Config(
            boolean PrimaryKey,
            boolean Unique,
            boolean NotNull
    ) {
        public static Config getDefault() {
            return new Config(false, false, true);
        }
        public static Config getKey() {
            return new Config(true, true, true);
        }
        public static Config getNullable() {
            return new Config(false, false, false);
        }

        @Override
        public String toString() {
            if (PrimaryKey) {
                return "PRIMARY KEY";
            } else if (Unique) {
                return "UNIQUE";
            } else if (NotNull) return "NOT NULL";
            return "";
        }
    }
}
