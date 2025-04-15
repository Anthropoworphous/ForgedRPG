package com.github.treesontop.database.statement;

import com.github.treesontop.database.DataBase;
import com.github.treesontop.database.generator.TableGenerator;
import com.github.treesontop.database.table.MockTable;
import com.github.treesontop.database.table.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

class SQLCreateTableTest {
    private static Logger logger = Logger.getLogger(SQLCreateTable.class.getName());
    private Table table;
    private static Connection connection;

    @BeforeAll
    static void init() throws SQLException, InvalidObjectException {
        connection = DataBase.setupDataBase("jdbc:sqlite::memory:");
    }

    @BeforeEach
    void setUp() {
        table = TableGenerator.generateTable(MockTable.class);
    }

    @Test
    void testCompile() {
        var statement = new SQLCreateTable(table);

        String result = statement.compile();

        logger.info(result);

        try {
            statement.execute(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
