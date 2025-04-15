package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLTableLinkTest {

    private Table table1;
    private Table table2;
    private Column column1;
    private Column column2;
    private Column column3;
    private Column column4;

    @BeforeEach
    public void setUp() {
        table1 = new Table.Builder("table1")
                .insertColumn(new Column("column1", SQLDataType.INT, Column.Config.DEFAULT))
                .insertColumn(new Column("column2", SQLDataType.TEXT, Column.Config.DEFAULT))
                .build();
        table2 = new Table.Builder("table2")
                .insertColumn(new Column("column3", SQLDataType.INT, Column.Config.DEFAULT))
                .insertColumn(new Column("column4", SQLDataType.TEXT, Column.Config.DEFAULT))
                .build();
        column1 = table1.column("column1");
        column2 = table1.column("column2");
        column3 = table2.column("column3");
        column4 = table2.column("column4");
    }

    @Test
    public void testSQLTableLink() {
        SQLTableLink link = new SQLTableLink(table1, table2, new SQLTableLink.ColumnLink(column1, column3));
        assertEquals("INNER JOIN table2 ON column1 = column3", link.toString());
    }

    @Test
    public void testColumnLink() {
        SQLTableLink.ColumnLink columnLink = new SQLTableLink.ColumnLink(column1, column3);
        assertEquals("column1 = column3", columnLink.toString());
    }
}
