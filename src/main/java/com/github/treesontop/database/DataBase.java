package com.github.treesontop.database;

import com.github.treesontop.Main;

import java.io.InvalidObjectException;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;


public class DataBase {

    private static Connection sqlConn;

    public static void connectToDB() {
        var url = "jdbc:sqlite:C:/Users/kevin/IdeaProjects/ForgeRPG/TempSQLDataBase/data.db";
        try {
            DataBase.setupDataBase(url);
            Main.logger.info("Connection to SQLite has been established.");
        } catch (SQLException e) {
            Main.logger.log(Level.SEVERE, "Database connection error", e);
        } catch (InvalidObjectException e) {
            Main.logger.log(Level.SEVERE, "Database setup error", e);
            throw new RuntimeException(e);
        }
    }

    public static Connection setupMemoryDataBase() throws SQLException, InvalidObjectException { return setupDataBase("jdbc:sqlite::memory:", true); }

    public static Connection setupDataBase(String url) throws InvalidObjectException, SQLException {
        if (sqlConn != null) {
            String errorMessage = "Database is already set up";
            Main.logger.severe(errorMessage);
            throw new InvalidObjectException(errorMessage);
        }

        return setupDataBase(url, true);
    }
    public static Connection setupDataBase(String url, boolean force) throws InvalidObjectException, SQLException {
        if (!force) return setupDataBase(url);

        sqlConn = DriverManager.getConnection(url);
        Main.logger.info("Database connection has been set up");
        return sqlConn;
    }

    public static void closeDataBase() throws SQLException {
        if (sqlConn == null) {
            Main.logger.info("Database connection is not set up");
        } else {
            sqlConn.close();
        }
    }

    public static void wipeConnection() {
        sqlConn = null;
    }

    public static PreparedStatement getStatement(String sql) throws SQLException {
        if (sqlConn == null) {
            String errorMessage = "Database connection is not set up";
            Main.logger.severe(errorMessage);
            throw new SQLException(errorMessage);
        }
        return sqlConn.prepareStatement(sql);
    }

    public static Optional<ResultSet> runStatement(String sql) throws SQLException {
        if (sqlConn == null) {
            String errorMessage = "Database connection is not set up";
            Main.logger.severe(errorMessage);
            throw new SQLException(errorMessage);
        }

        Main.logger.info("Running SQL statement: ");
        Main.logger.info(sql);

        var statement = sqlConn.createStatement();
        return statement.execute(sql) ? Optional.of(statement.getResultSet()) : Optional.empty();
    }
}
