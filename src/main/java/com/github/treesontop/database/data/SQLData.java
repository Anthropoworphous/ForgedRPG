package com.github.treesontop.database.data;

import com.github.treesontop.database.SQLDataType;

public abstract class SQLData {
    public final SQLDataType dataType;
    protected final Object value;

    public SQLData(SQLDataType dataType, Object value) {
        this.dataType = dataType;
        this.value = value;
    }

    public abstract String sqlForm();
}
