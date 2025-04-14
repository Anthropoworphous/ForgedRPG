package com.github.treesontop.database.generator;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MakeColumn {
    String name();
    SQLDataType type();
    Column.Config config() default Column.Config.DEFAULT;
}
