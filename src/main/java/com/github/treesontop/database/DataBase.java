package com.github.treesontop.database;

import com.github.treesontop.Main;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DataBase {
    private static final Logger logger = Logger.getLogger(DataBase.class.getName());
    private static Connection sqlConn;

    static {
        logger.setParent(Logger.getLogger(Main.class.getName()));
    }

    public static Connection setupDataBase(String url) throws InvalidObjectException, SQLException {
        if (sqlConn != null) {
            String errorMessage = "Database is already set up";
            logger.severe(errorMessage);
            throw new InvalidObjectException(errorMessage);
        }
        sqlConn = DriverManager.getConnection(url);
        logger.info("Database connection has been set up");
        return sqlConn;
    }

    public static void closeDataBase() throws SQLException {
        sqlConn.close();
    }

    public static PreparedStatement getStatement(String sql) throws SQLException {
        if (sqlConn == null) {
            String errorMessage = "Database connection is not set up";
            logger.severe(errorMessage);
            throw new SQLException(errorMessage);
        }
        return sqlConn.prepareStatement(sql);
    }

    public static void runStatement(String sql) throws SQLException {
        if (sqlConn == null) {
            String errorMessage = "Database connection is not set up";
            logger.severe(errorMessage);
            throw new SQLException(errorMessage);
        }

        logger.info("Running SQL statement: ");
        logger.info(sql);

        sqlConn.createStatement().execute(sql);
    }
}
