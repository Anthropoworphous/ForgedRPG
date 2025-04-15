package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLGroupByTest {

    @Test
    public void testSQLGroupByToString() {
        SQLValue mockValue1 = mock(SQLValue.class);
        SQLValue mockValue2 = mock(SQLValue.class);
        when(mockValue1.toString()).thenReturn("mockValue1");
        when(mockValue2.toString()).thenReturn("mockValue2");

        SQLGroupBy groupBy = new SQLGroupBy(List.of(mockValue1, mockValue2));
        assertEquals("GROUP BY mockValue1, mockValue2", groupBy.toString());
    }

    @Test
    public void testHaving() {
        SQLValue mockValue1 = mock(SQLValue.class);
        SQLValue mockValue2 = mock(SQLValue.class);
        SQLCondition mockCondition = mock(SQLCondition.class);
        when(mockValue1.toString()).thenReturn("mockValue1");
        when(mockValue2.toString()).thenReturn("mockValue2");
        when(mockCondition.toString()).thenReturn("mockCondition");

        SQLGroupBy groupBy = new SQLGroupBy(List.of(mockValue1, mockValue2));
        groupBy.having(mockCondition);
        assertEquals("GROUP BY mockValue1, mockValue2\nHAVING mockCondition", groupBy.toString());
    }
}
