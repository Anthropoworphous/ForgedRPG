package com.github.treesontop.database;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Column {
    public final SQLDataType dataType;
    public final Config config;

    public Column(SQLDataType dataType) {
        this.dataType = dataType;
        config = Config.getDefault();
    }
    public Column(SQLDataType dataType, Config config) {
        this.dataType = dataType;
        this.config = config;
    }



    public record Config(
            boolean PrimaryKey,
            boolean Unique,
            boolean NotNull
    )
    {
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
