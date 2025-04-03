package com.github.treesontop.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataBaseTest {

    private Connection connection;
    private static final String url = "jdbc:sqlite::memory:";

    @BeforeEach
    public void setUp() throws SQLException, InvalidObjectException {
        connection = DataBase.setupDataBase(url);
    }

    @Test
    public void testSetupDataBase() {
        assertNotNull(connection);
    }

    @Test
    public void testGetStatement() throws SQLException {
        PreparedStatement statement = DataBase.getStatement("SELECT 1");
        assertNotNull(statement);
    }

    @Test
    public void testGetStatementWithoutSetup() {
        SQLException exception = assertThrows(SQLException.class, () -> DataBase.getStatement("SELECT 1"));
        assertEquals("Database connection is not set up", exception.getMessage());
    }
}
