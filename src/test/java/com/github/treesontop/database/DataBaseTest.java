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

    @BeforeEach
    public void setUp() throws SQLException {
        String url = "jdbc:sqlite::memory:";
        connection = DriverManager.getConnection(url);
    }

    @Test
    public void testSetupDataBase() throws InvalidObjectException {
        DataBase.setupDataBase(connection);
        assertNotNull(connection);
    }

    @Test
    public void testSetupDataBaseAlreadySetUp() throws InvalidObjectException {
        DataBase.setupDataBase(connection);
        InvalidObjectException exception = assertThrows(InvalidObjectException.class, () -> DataBase.setupDataBase(connection));
        assertEquals("Database is already set up", exception.getMessage());
    }

    @Test
    public void testGetStatement() throws InvalidObjectException, SQLException {
        DataBase.setupDataBase(connection);
        PreparedStatement statement = DataBase.getStatement("SELECT 1");
        assertNotNull(statement);
    }

    @Test
    public void testGetStatementWithoutSetup() {
        SQLException exception = assertThrows(SQLException.class, () -> DataBase.getStatement("SELECT 1"));
        assertEquals("Database connection is not set up", exception.getMessage());
    }
}
