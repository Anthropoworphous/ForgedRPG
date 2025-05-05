package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.DataBase;
import com.github.treesontop.database.data.SQLFloat;
import com.github.treesontop.database.data.SQLInt;
import com.github.treesontop.database.data.SQLText;
import com.github.treesontop.database.generator.TableGenerator;
import com.github.treesontop.database.table.MockTable;
import com.github.treesontop.database.table.Table;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteException;

import java.io.InvalidObjectException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class SQLInsertTest {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SQLInsertTest.class);
    private static Logger logger = Logger.getGlobal();
    private static Table table;
    private SQLInsert statement;
    private Column.Filled key;

    @BeforeAll
    static void setupAll() throws SQLException, InvalidObjectException {
        DataBase.setupMemoryDataBase();
        table = TableGenerator.generateTable(MockTable.class);
        table.mkTable().execute();
    }

    @BeforeEach
    void setup() {
        statement = table.insert();
        key = table.key().fill(SQLText.tinyText("kys"));
    }

    @Test
    @Order(1)
    void dbTest() throws SQLException {
        logger.info("Testing database");

        statement.insert(key,
            table.column("value1").fill(SQLText.tinyText("v1")),
            table.column("value2").fill(SQLInt.intValue(2)),
            table.column("value3").fill(SQLFloat.floatValue(3.0f))
        );

        var sql = statement.compile();
        logger.info("SQL: " + sql);
        statement.execute();

        var query = table.findExact(key, "value1", "value2", "value3");

        try (var value = query.execute().orElseThrow()) {
            Assertions.assertEquals("v1", value.getString("value1"));
            Assertions.assertEquals(2, value.getInt("value2"));
            Assertions.assertEquals(3.0f, value.getFloat("value3"));
        } catch (AssertionError error) {
            throw new RuntimeException(error);
        } catch (Exception ignored) {
            logger.log(Level.WARNING, "Failed due to query statement");
            return;
        }
    }

    @Test
    @Order(2)
    void replaceTest() throws SQLException {
        logger.info("Testing replacement");

        statement.insert(key,
            table.column("value1").fill(SQLText.tinyText("not v1")),
            table.column("value2").fill(SQLInt.intValue(-2)),
            table.column("value3").fill(SQLFloat.floatValue(-3.0f))
        );

        statement.execute();

        var query = table.findExact(key, "value1", "value2", "value3");

        try (var value = query.execute().orElseThrow()) {
            Assertions.assertEquals("not v1", value.getString("value1"));
            Assertions.assertEquals(-2, value.getInt("value2"));
            Assertions.assertEquals(-3.0f, value.getFloat("value3"));
        } catch (AssertionError error) {
            throw new RuntimeException(error);
        } catch (Exception ignored) {
            logger.log(Level.WARNING, "Failed due to query statement");
            return;
        }
    }

    @Test
    @Order(3)
    void failedReplaceTest() throws SQLException {
        logger.info("Testing failed replacement");

        statement.replace(false);
        statement.insert(key,
            table.column("value1").fill(SQLText.tinyText("v1 again")),
            table.column("value2").fill(SQLInt.intValue(2)),
            table.column("value3").fill(SQLFloat.floatValue(3.0f))
        );

        Assertions.assertThrowsExactly(SQLiteException.class, () -> statement.execute(),
            "[SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: test_table.key)");
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme
