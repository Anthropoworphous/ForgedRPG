package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.statement.label.SQLValue;
import com.github.treesontop.database.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLSelectTest {

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
    public void testSQLSelect() {
        SQLSelect select = new SQLSelect(column1, column2);
        assertEquals("SELECT column1, column2\nFROM table1\n;", select.compile());
    }

    @Test
    public void testLimit() {
        SQLSelect select = new SQLSelect(column1, column2).limit(10);
        assertEquals("SELECT column1, column2\nFROM table1\nLIMIT 10\n;", select.compile());
    }

    @Test
    public void testCondition() {
        SQLValue mockValue = mock(SQLValue.class);
        when(mockValue.toString()).thenReturn("mockValue");

        SQLCondition condition = new SQLCondition(column1, SQLCondition.Mode.EQUAL, mockValue);
        SQLSelect select = new SQLSelect(column1, column2).condition(condition);
        assertEquals("SELECT column1, column2\nFROM table1\ncolumn1 = mockValue\n;", select.compile());
    }

    @Test
    public void testOrderBy() {
        SQLOrderBy orderBy = new SQLOrderBy(new SQLOrderBy.By(column1, SQLOrderBy.Mode.ASCENDING));
        SQLSelect select = new SQLSelect(column1, column2).orderBy(orderBy);
        assertEquals("SELECT column1, column2\nFROM table1\nORDER BY column1 ASC\n;", select.compile());
    }

    @Test
    public void testLinkTable() {
        SQLTableLink link = new SQLTableLink(table1, table2, new SQLTableLink.ColumnLink(column1, column3));
        SQLSelect select = new SQLSelect(column1, column2).linkTable(link);
        assertEquals("SELECT column1, column2\nFROM table1, table2\nINNER JOIN table2 ON column1 = column3\n;", select.compile());
    }
}
