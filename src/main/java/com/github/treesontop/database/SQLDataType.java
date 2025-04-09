package com.github.treesontop.database;

public enum SQLDataType {
    //Text
    TINYTEXT("TINYTEXT"), //255 chars
    MEDIUMTEXT("MEDIUMTEXT"),
    LONGTEXT("LONGTEXT"),

    //Numerical
    BYTE("TINYINT"),
    SHORT("SMALLINT"),
    INT("INT"),
    LONG("BIGINT"),
    U_BYTE("TINYINT UNSIGNED"),
    U_SHORT("SMALLINT UNSIGNED"),
    U_INT("INT UNSIGNED"),
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
