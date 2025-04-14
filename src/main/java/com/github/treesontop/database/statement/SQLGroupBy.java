package com.github.treesontop.database.statement;

import com.github.treesontop.database.statement.label.SQLValue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SQLGroupBy {
    private final List<SQLValue> groupBy;
    private SQLCondition having = null;

    public SQLGroupBy(List<SQLValue> groupBy) {
        this.groupBy = groupBy;
    }

    public boolean valueCheck(Collection<SQLValue> values) {
        return values.containsAll(groupBy) && (having == null || groupBy.contains(having.target));
    }

    public SQLGroupBy having(SQLCondition condition) {
        having = condition;
        return this;
    }

    @Override
    public String toString() {
        var str = "GROUP BY " + groupBy.stream().map(SQLValue::toString).collect(Collectors.joining(", "));
        if (having != null) str += "\nHAVING " + having;
        return str;
    }
}
