package com.github.treesontop.database.table;

import com.github.treesontop.Util;
import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.generator.GenerateTable;
import com.github.treesontop.database.generator.MakeColumn;

@Util.DoNotScan
@GenerateTable(name = "test_table")
public class MockTable {
    @MakeColumn(name = "key", type = SQLDataType.TINYTEXT, config = Column.Config.KEY)
    public String key;

    @MakeColumn(name = "value1", type = SQLDataType.TINYTEXT)
    public String value1;
    @MakeColumn(name = "value2", type = SQLDataType.INT)
    public int value2;
    @MakeColumn(name = "value3", type = SQLDataType.FLOAT)
    public float value3;
}
