package com.github.treesontop.database.data;

import com.github.treesontop.database.SQLDataType;

public class SQLText extends SQLData {
    public SQLText(SQLDataType type, String text) {
        super(type, text);
    }

    @Override
    public String sqlForm() throws NullPointerException {
        var txt = value.toString();
        switch (dataType) {
            case TINYTEXT -> { if (txt.length() > 255) throw new RuntimeException("Too long"); }
            case MEDIUMTEXT -> { if (txt.length() > 16777215) throw new RuntimeException("Too long"); }
            case LONGTEXT -> {}
            default -> throw new RuntimeException("Invalid type");
        }
        return "'" + txt + "'";
    }

    public static SQLText tinyText(String text) {
        return new SQLText(SQLDataType.TINYTEXT, text);
    }
    public static SQLText mediumText(String text) {
        return new SQLText(SQLDataType.MEDIUMTEXT, text);
    }
    public static SQLText longText(String text) {
        return new SQLText(SQLDataType.LONGTEXT, text);
    }
}
