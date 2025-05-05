package com.github.treesontop.database.generator;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.DataBase;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.data.SQLFloat;
import com.github.treesontop.database.data.SQLInt;
import com.github.treesontop.database.data.SQLText;
import com.github.treesontop.database.table.MockTable;
import com.github.treesontop.database.table.Row;
import com.github.treesontop.database.table.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

class TableGeneratorTest {
    Logger logger = Logger.getGlobal();
    Connection conn;

    @BeforeEach
    void setup() throws SQLException, InvalidObjectException {
        conn = DataBase.setupMemoryDataBase();
        Table.reset();
    }

    @Test
    void testGenerate() throws NoSuchFieldException, SQLException {
        TableGenerator.generate(Collections.singleton(MockTable.class));
        var table = Table.find("test_table");
        var statement = table.insert();

        statement.insert(
                table.key().fill(new SQLText(SQLDataType.TINYTEXT, "k1")),
                table.column("value1").fill(new SQLText(SQLDataType.TINYTEXT, "kys")),
                table.column("value2").fill(new SQLInt(SQLDataType.INT, 69)),
                table.column("value3").fill(new SQLFloat(SQLDataType.FLOAT, 4.20f))
        );

        logger.info("SQL Statement ==========\n" + statement.compile());

        statement.execute(conn);
    }

    @Test
    void testProvideTableReference() {
        MockTable.table = null;
        var table = TableGenerator.generateTable(MockTable.class);
        TableGenerator.provideTableReference(MockTable.class, table);
        Assertions.assertEquals(MockTable.table, table);
    }
}
