package com.github.treesontop.database;

public enum SQLDataType {
    //Text
    TINYTEXT("TINYTEXT"), //255 chars
    MEDIUMTEXT("MEDIUMTEXT"),
    LONGTEXT("LONGTEXT"),

    //Numerical
    SHORT("SMALLINT"),
    INT("INT"),
    LONG("BIGINT"),
    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),

    //Chronological
    DATE("DATE"),
    DATETIME("DATETIME"),
    TIMESTAMP("TIMESTAMP"),
    TIME("TIME");

    public final String type;

    SQLDataType(String type) {
        this.type = type;
    }
}
