package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.SQLCondition.Mode;
import com.github.treesontop.database.statement.label.SQLValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLConditionTest {

    @Test
    public void testSQLConditionToString() {
        SQLValue mockTarget = mock(SQLValue.class);
        SQLValue mockValue = mock(SQLValue.class);
        when(mockTarget.toString()).thenReturn("mockTarget");
        when(mockValue.toString()).thenReturn("mockValue");

        SQLCondition condition = new SQLCondition(mockTarget, Mode.EQUAL, mockValue);
        assertEquals("mockTarget = mockValue", condition.toString());

        condition = new SQLCondition(mockTarget, Mode.NOT_EQUAL, mockValue);
        assertEquals("mockTarget != mockValue", condition.toString());
    }

    @Test
    public void testModeEnum() {
        assertEquals("EQUAL", Mode.EQUAL.name());
        assertEquals("NOT_EQUAL", Mode.NOT_EQUAL.name());
        assertEquals("LESS_THAN", Mode.LESS_THAN.name());
        assertEquals("GREATER_THAN", Mode.GREATER_THAN.name());
        assertEquals("LESS_THAN_OR_EQUAL_TO", Mode.LESS_THAN_OR_EQUAL_TO.name());
        assertEquals("GREATER_THAN_OR_EQUAL_TO", Mode.GREATER_THAN_OR_EQUAL_TO.name());
        assertEquals("LIKE", Mode.LIKE.name());
        assertEquals("IN", Mode.IN.name());
        assertEquals("NOT_IN", Mode.NOT_IN.name());
        assertEquals("BETWEEN", Mode.BETWEEN.name());
    }

    @Test
    public void testInvalidTargetCheck() {
        SQLValue mockTarget = mock(SQLValue.class);
        SQLValue mockValue = mock(SQLValue.class);
        when(mockTarget.toString()).thenReturn("mockTarget");
        when(mockValue.toString()).thenReturn("mockValue");

        SQLCondition condition = new SQLCondition(mockTarget, Mode.IN, mockValue);
        assertThrows(RuntimeException.class, condition::toString);
    }

    @Test
    public void testInvalidValueCheck() {
        SQLValue mockTarget = mock(SQLValue.class);
        SQLValue mockValue = mock(SQLValue.class);
        when(mockTarget.toString()).thenReturn("mockTarget");
        when(mockValue.toString()).thenReturn("mockValue");

        SQLCondition condition = new SQLCondition(mockTarget, Mode.BETWEEN, mockValue);
        assertThrows(RuntimeException.class, condition::toString);
    }
}
