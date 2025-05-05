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


    public static SQLInt byteValue(int value) {
        return new SQLInt(SQLDataType.BYTE, value);
    }
    public static SQLInt shortValue(int value) {
        return new SQLInt(SQLDataType.SHORT, value);
    }
    public static SQLInt intValue(int value) {
        return new SQLInt(SQLDataType.INT, value);
    }
    public static SQLInt longValue(int value) {
        return new SQLInt(SQLDataType.LONG, value);
    }
    public static SQLInt longValue(long value) {
        return new SQLInt(SQLDataType.LONG, value);
    }

    public static class Unsigned {
        public static SQLInt byteValue(int value) {
            return new SQLInt(SQLDataType.U_BYTE, value);
        }
        public static SQLInt shortValue(int value) {
            return new SQLInt(SQLDataType.U_SHORT, value);
        }
        public static SQLInt intValue(int value) {
            return new SQLInt(SQLDataType.U_INT, value);
        }
    }
}
