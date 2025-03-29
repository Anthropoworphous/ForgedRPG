package com.github.treesontop.database.setup.processor;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface MakeColumn {
    SQLDataType datatype();
    Config config() default Config.DEFAULT;

    enum Config {
        DEFAULT(Column.Config::getDefault),
        KEY(Column.Config::getKey),
        NULLABLE(Column.Config::getNullable);

        final Supplier<Column.Config> supplier;

        Config(Supplier<Column.Config> supplier) {
            this.supplier = supplier;
        }

        public Column.Config get() { return supplier.get(); }
    }
}
