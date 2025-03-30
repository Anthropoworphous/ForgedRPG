package com.github.treesontop.database;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final Logger logger = Logger.getLogger(DataBase.class.getName());
    private static Connection _sqlConn;

    /**
     * Sets up the database connection.
     *
     * @param conn the database connection
     * @throws InvalidObjectException if the database is already set up
     */
    public static void setupDataBase(Connection conn) throws InvalidObjectException {
        if (_sqlConn != null) {
            String errorMessage = "Database is already set up";
            logger.severe(errorMessage);
            throw new InvalidObjectException(errorMessage);
        }
        _sqlConn = conn;
        logger.info("Database connection has been set up");
    }

    /**
     * Prepares a SQL statement.
     *
     * @param sql the SQL query
     * @return the prepared statement
     * @throws SQLException if a database access error occurs
     */
    public static PreparedStatement getStatement(String sql) throws SQLException {
        if (_sqlConn == null) {
            String errorMessage = "Database connection is not set up";
            logger.severe(errorMessage);
            throw new SQLException(errorMessage);
        }
        return _sqlConn.prepareStatement(sql);
    }
}
