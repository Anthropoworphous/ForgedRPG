package com.github.treesontop.database;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {
    private static Connection _sqlConn;

    public static void setupDataBase(Connection conn) throws InvalidObjectException {
        if (_sqlConn != null) { throw new InvalidObjectException("Already setup"); }
    }

    public static PreparedStatement getStatement(String sql) throws SQLException {
        return _sqlConn.prepareStatement(sql);
    }
}
