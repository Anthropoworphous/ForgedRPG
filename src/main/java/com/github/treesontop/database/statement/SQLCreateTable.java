package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.statement.label.SQLStatement;
import com.github.treesontop.database.table.Table;

import java.util.ArrayList;

public class SQLCreateTable implements SQLStatement {
    public final Table table;

    private boolean ifNotExist = true;
    private String schema = "";
    private boolean withoutRowID = true;

    public SQLCreateTable(Table table) {
        this.table = table;
    }

    public SQLCreateTable ifNotExist(boolean ifNotExist) {
        this.ifNotExist = ifNotExist;
        return this;
    }
    public SQLCreateTable schema(String schema) {
        this.schema = schema;
        return this;
    }
    public SQLCreateTable withoutRowID(boolean withoutRowID) {
        this.withoutRowID = withoutRowID;
        return this;
    }

    @Override
    public String compile() {
        StringBuilder str = new StringBuilder("CREATE TABLE");
        if (ifNotExist) str.append(" IF NOT EXISTS ");
        if (!schema.isBlank()) str.append(schema).append(".");
        str.append(table.name).append(" (\n");

        var columnStr = new ArrayList<String>();
        addColumn(columnStr, table.key());
        for (Column column : table.columns()) {
            addColumn(columnStr, column);
        }
        str.append(String.join(",\n", columnStr)).append("\n)");

        if (withoutRowID) str.append(" WITHOUT ROWID");

        return str.toString() + ';';
    }

    private void addColumn(ArrayList<String> list, Column column) {
        list.add("    %s %s %s".formatted(
            column.name,
            column.dataType.toString(),
            column.config().toString()
        ));
    }
}
