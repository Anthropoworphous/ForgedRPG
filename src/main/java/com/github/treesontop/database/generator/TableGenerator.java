package com.github.treesontop.database.generator;

import com.github.treesontop.Util;
import com.github.treesontop.database.table.Table;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class TableGenerator {
    private static final Logger logger = Logger.getGlobal();

    public static void generate() {
        var classes = Util.getAnnotatedClass("com.github.treesontop", GenerateTable.class);
        logger.info("Generating " + classes.size() + " tables");

        for (Class<?> clazz : classes) {
            Table table = generateTable(clazz);
            provideTableReference(clazz, table);
            table.register();
            try {
                table.mkTable().execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            logger.info("Generated " + table.name);
        }
    }

    public static Table generateTable(Class<?> clazz) {
        var table = new Table.Builder(clazz.getAnnotation(GenerateTable.class).name());
        Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(MakeColumn.class))
            .map(marked -> marked.getAnnotation(MakeColumn.class))
            .forEach(table::insertColumn);
        return table.build();
    }

    public static void provideTableReference(Class<?> clazz, Table table) {
        try {
            var field = clazz.getDeclaredField("table");
            if (field.getType() != Table.class) {
                logger.info(clazz.getName() + " have a field named \"table\", which is used to insert a reference");
            } else {
                field.setAccessible(true);
                field.set(table, table);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            logger.info(clazz.getName() + " does not have a \"table\" field");
        }
    }
}
