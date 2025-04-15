package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.SQLAggregate.Mode;
import com.github.treesontop.database.statement.label.SQLValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLAggregateTest {

    @Test
    public void testSQLAggregateToString() {
        SQLValue mockValue = mock(SQLValue.class);
        when(mockValue.toString()).thenReturn("mockValue");

        SQLAggregate aggregate = new SQLAggregate(mockValue, Mode.COUNT, true);
        assertEquals("COUNT(DISTINCT mockValue)", aggregate.toString());

        aggregate = new SQLAggregate(mockValue, Mode.SUM, false);
        assertEquals("SUM(mockValue)", aggregate.toString());
    }

    @Test
    public void testModeEnum() {
        assertEquals("AVG", Mode.AVG.name());
        assertEquals("COUNT", Mode.COUNT.name());
        assertEquals("MAX", Mode.MAX.name());
        assertEquals("MIN", Mode.MIN.name());
        assertEquals("SUM", Mode.SUM.name());
    }
}
