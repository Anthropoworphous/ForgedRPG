package com.github.treesontop.database.data;

import com.github.treesontop.database.SQLDataType;

public class SQLFloat extends SQLData {
    public SQLFloat(SQLDataType dataType, double value) {
        super(dataType, value);
    }
    public SQLFloat(SQLDataType dataType, float value) {
        super(dataType, value);
    }

    @Override
    public String sqlForm() {
        switch (dataType) {
            case FLOAT -> {
                var numF = (float) value;
                if (numF > Float.MAX_VALUE || numF < -Float.MAX_VALUE) throw new RuntimeException("Invalid number");
                return Float.toString(numF);
            }
            case DOUBLE -> {
                var numD = (double) value;
                if (numD > Double.MAX_VALUE || numD < -Double.MAX_VALUE) throw new RuntimeException("Invalid number");
                return Double.toString(numD);
            }
        }
        throw new RuntimeException("Invalid type");
    }

    public static SQLFloat floatValue(float value) {
        return new SQLFloat(SQLDataType.FLOAT, value);
    }
    public static SQLFloat doubleValue(double value) {
        return new SQLFloat(SQLDataType.DOUBLE, value);
    }
}
