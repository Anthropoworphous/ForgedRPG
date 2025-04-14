package com.github.treesontop.database.statement;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.statement.label.SQLGroup;
import com.github.treesontop.database.table.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SQLTableLink implements SQLGroup {
    public final Table table1;
    public final Table table2;
    private final boolean leftJoin;

    private final Set<ColumnLink> links = new HashSet<>();

    public SQLTableLink(Table table1, Table table2, boolean isLeftJoin, ColumnLink link, ColumnLink... moreLink) {
        this.table1 = table1;
        this.table2 = table2;
        this.leftJoin = isLeftJoin;

        links.add(link);
        links.addAll(List.of(moreLink));
    }
    public SQLTableLink(Table table1, Table table2, ColumnLink link, ColumnLink... moreLink) {
        this(table1, table2, false, link, moreLink);
    }

    @Override
    public String toString() {
        return "%s JOIN %s ON %s".formatted(
            leftJoin ? "LEFT" : "INNER",
            table2.name,
            links.stream().map(ColumnLink::toString).collect(Collectors.joining(" AND "))
        );
    }

    public record ColumnLink(Column column1, Column column2) {
        @Override
        public String toString() {
            return column1.toString() + " = " + column2.toString();
        }
    }
}
