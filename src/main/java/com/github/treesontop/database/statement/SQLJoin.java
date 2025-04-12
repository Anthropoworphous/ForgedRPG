package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;

public class SQLJoin {

    public record Link(Column left, Column right) {
        @Override
        public String toString() {
            return "";
        }
    }
}
