package com.github.treesontop.database;

import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataBaseTest {
    @Test
    public void testGetStatement() throws SQLException, InvalidObjectException {
        DataBase.setupMemoryDataBase();
        var qry = DataBase.getStatement("SELECT 1");
        qry.execute();
        assertEquals(1, qry.getResultSet().getInt(1));
    }

    @Test
    public void testGetStatementWithoutSetup() throws SQLException {
        DataBase.closeDataBase();
        DataBase.wipeConnection();
        SQLException exception = assertThrows(SQLException.class, () -> DataBase.getStatement("SELECT 1"));
        assertEquals("Database connection is not set up", exception.getMessage());
    }
}
