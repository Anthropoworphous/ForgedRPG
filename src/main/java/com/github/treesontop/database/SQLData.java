package com.github.treesontop.database;

import java.util.Objects;

public abstract class SQLData {
    public final SQLDataType dataType;
    public final Object value;

    public SQLData(SQLDataType dataType, Object value) {
        this.dataType = dataType;
        this.value = value;
    }

    public abstract String sqlForm();
}
