package com.github.treesontop.database.table;

import com.github.treesontop.database.DataBase;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.data.SQLFloat;
import com.github.treesontop.database.data.SQLInt;
import com.github.treesontop.database.data.SQLText;
import com.github.treesontop.database.generator.TableGenerator;
import org.junit.jupiter.api.*;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TableTest {
    private static final Logger logger = Logger.getGlobal();
    private static Table table;
    private static Connection connection;

    @BeforeAll
    static void init() throws SQLException, InvalidObjectException {
        DataBase.closeDataBase();
        DataBase.wipeConnection();
        connection = DataBase.setupDataBase("jdbc:sqlite::memory:");
        table = TableGenerator.generateTable(MockTable.class);
    }

    @Test
    @Order(1)
    void testMkTable() throws SQLException {
        var statement = table.mkTable();

        logger.info("SQL Statement ==========\n" + statement.compile());

        statement.execute(connection);
    }

    @Test
    @Order(2)
    void testInsert() throws SQLException {
        var statement = table.insert();
        statement.insert(
            table.key().fill(new SQLText(SQLDataType.TINYTEXT, "k1")),
            table.column("value1").fill(new SQLText(SQLDataType.TINYTEXT, "kys")),
            table.column("value2").fill(new SQLInt(SQLDataType.INT, 69)),
            table.column("value3").fill(new SQLFloat(SQLDataType.FLOAT, 4.20f))
        );

        logger.info("SQL Statement ==========\n" + statement.compile());

        statement.execute(connection);
    }

    @Test
    @Order(3)
    void testFindExact() throws SQLException {
        var statement = table.findExact(
            table.key().fill(new SQLText(SQLDataType.TINYTEXT, "k1")),
            "value1", "value2", "value3"
        );

        logger.info("SQL Statement ==========\n" + statement.compile());

        var result = statement.execute(connection);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("kys", result.get().getString("value1"));
    }
}

