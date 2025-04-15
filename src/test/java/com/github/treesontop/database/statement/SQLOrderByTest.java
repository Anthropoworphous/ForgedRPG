package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.SQLOrderBy.By;
import com.github.treesontop.database.statement.SQLOrderBy.Mode;
import com.github.treesontop.database.statement.label.SQLValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLOrderByTest {

    @Test
    public void testSQLOrderByToString() {
        SQLValue mockValue1 = mock(SQLValue.class);
        SQLValue mockValue2 = mock(SQLValue.class);
        when(mockValue1.toString()).thenReturn("mockValue1");
        when(mockValue2.toString()).thenReturn("mockValue2");

        SQLOrderBy orderBy = new SQLOrderBy(new By(mockValue1, Mode.ASCENDING), new By(mockValue2, Mode.DESCENDING));
        assertEquals("ORDER BY mockValue1 ASC, mockValue2 DESC", orderBy.toString());
    }

    @Test
    public void testByToString() {
        SQLValue mockValue = mock(SQLValue.class);
        when(mockValue.toString()).thenReturn("mockValue");

        By by = new By(mockValue, Mode.ASCENDING);
        assertEquals("mockValue ASC", by.toString());
    }

    @Test
    public void testModeToString() {
        assertEquals("ASC", Mode.ASCENDING.toString());
        assertEquals("DESC", Mode.DESCENDING.toString());
    }
}
