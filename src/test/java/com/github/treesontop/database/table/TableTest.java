package com.github.treesontop.database.table;

import com.github.treesontop.Util;
import com.github.treesontop.database.Column;
import com.github.treesontop.database.DataBase;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.data.SQLText;
import com.github.treesontop.database.generator.GenerateTable;
import com.github.treesontop.database.generator.MakeColumn;
import com.github.treesontop.database.generator.TableGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

class TableTest {
    private static final Logger logger = Logger.getLogger(TableTest.class.getName());
    private Table table;
    private static Connection connection;

    @BeforeAll
    static void init() throws SQLException, InvalidObjectException {
        connection = DataBase.setupDataBase("jdbc:sqlite::memory:");
    }

    @BeforeEach
    void setUp() {
        table = TableGenerator.generateTable(MockTableGenerater.class);
    }

    @Test
    void testFindExact() {
        var statement = table.findExact(
            Set.of(table.column("key").fill(new SQLText(SQLDataType.TINYTEXT, "k1"))),
            "value1", "value2", "value3"
        );
        logger.info(statement.compile());
    }

    @Util.DoNotScan
    @GenerateTable(name = "test_table")
    public static class MockTableGenerater {
        @MakeColumn(name = "key", type = SQLDataType.TINYTEXT, config = Column.Config.KEY)
        public String key;

        @MakeColumn(name = "value1", type = SQLDataType.TINYTEXT)
        public String value1;
        @MakeColumn(name = "value2", type = SQLDataType.INT)
        public int value2;
        @MakeColumn(name = "value3", type = SQLDataType.FLOAT)
        public float value3;
    }
}

