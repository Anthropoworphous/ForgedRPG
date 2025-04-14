package com.github.treesontop.database.generator;

import com.github.treesontop.Util;
import com.github.treesontop.database.table.Table;

import java.util.Arrays;
import java.util.logging.Logger;

public class TableGenerator {
    private static final Logger logger = Logger.getLogger(TableGenerator.class.getName());

    public static void generate() {
        var classes = Util.getAnnotatedClass("com.github.treesontop", GenerateTable.class);
        logger.info("Generating " + classes.size() + " tables");

        classes.stream().map(TableGenerator::generateTable).forEach(Table::register);
    }

    public static Table generateTable(Class<?> clazz) {
        var table = new Table.Builder(clazz.getAnnotation(GenerateTable.class).name());
        Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(MakeColumn.class))
            .map(marked -> marked.getAnnotation(MakeColumn.class))
            .forEach(table::insertColumn);
        return table.build();
    }
}
