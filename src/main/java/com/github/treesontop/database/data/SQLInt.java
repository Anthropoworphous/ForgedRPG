package com.github.treesontop.database.data;

import com.github.treesontop.database.SQLDataType;

public class SQLInt extends SQLData {
    public SQLInt(SQLDataType dataType, long value) {
        super(dataType, value);
    }
    public SQLInt(SQLDataType dataType, int value) {
        super(dataType, (long) value);
    }

    @Override
    public String sqlForm() {
        var num = (long) value;
        switch (dataType) {
            case BYTE -> { if (num < -128 || num > 127) throw new RuntimeException("Too long"); }
            case SHORT -> { if (num < -32768 || num > 32767) throw new RuntimeException("Too long"); }
            case INT -> { if (num < -2147483648 || num > 2147483647) throw new RuntimeException("Too long"); }
            case LONG -> {}
            case U_BYTE -> { if (num < 0 || num > 255) throw new RuntimeException("Too long"); }
            case U_SHORT -> { if (num < 0 || num > 65535) throw new RuntimeException("Too long"); }
            case U_INT -> { if (num < 0 || num > 4294967295L) throw new RuntimeException("Too long"); }
            default -> throw new RuntimeException("Invalid type");
        }
        return Long.toString(num);
    }
}
