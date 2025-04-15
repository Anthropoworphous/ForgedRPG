package com.github.treesontop.database.statement.label;

import com.github.treesontop.database.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface SQLStatement {
    String compile();

    default Optional<ResultSet> execute(Connection connection) throws SQLException {
        var statement = connection.prepareStatement(compile());
        return statement.execute() ? Optional.of(statement.getResultSet()) : Optional.empty();
    }
    default Optional<ResultSet> execute() throws SQLException {
        var statement = DataBase.getStatement(compile());
        return statement.execute() ? Optional.of(statement.getResultSet()) : Optional.empty();
    }
}
