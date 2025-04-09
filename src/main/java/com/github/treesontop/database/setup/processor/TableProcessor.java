package com.github.treesontop.database.setup.processor;

import com.github.treesontop.codeGenerator.*;
import com.github.treesontop.database.Column;
import com.github.treesontop.database.data.SQLData;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.Table;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("com.github.treesontop.database.setup.processor.MakeColumn")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class TableProcessor extends AbstractProcessor {
    public TableProcessor() {
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            if (elements.isEmpty()) {
                continue;
            }

            var className = elements.stream().findAny().get().getEnclosingElement().getSimpleName().toString();

            var columnMap = new HashMap<String, MakeColumn>();
            for (var e : elements) {
                columnMap.put(e.getSimpleName().toString(), e.getAnnotation(MakeColumn.class));
            }

            try {
                writeTableFile(className, columnMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    private void writeTableFile(String className, Map<String, MakeColumn> columnMap) throws IOException {
        String tableClassName = className + "Table"; //for use in java
        String tableName = className.toLowerCase(); //for use in SQL

        try (CodeWriter out = new CodeWriter(processingEnv, "com.github.treesontop.database.setup", tableClassName)) {
            var classBlock = new ClassBlock(AccessModifiers.Scope.PUBLIC.get(),
                tableClassName,
                0,
                out.useType(GeneratedTable.class));

            classBlock.append(new Field(
                    AccessModifiers.Scope.PUBLIC.getStatic(),
                    new Parameter("table", out.useType(Table.class)))
            );
            classBlock.newLine();

            out.useType(Table.class);
            out.useType(Column.class);
            out.useType(SQLDataType.class);
            out.useType(Collectors.class);
            out.useType(MakeColumn.class);

            var strType = out.useType(String.class);

            var staticBlock = new CodeBlock.StaticBlock(1).append(
                    "var bdr = new Table.Builder(\"%s\");".formatted(tableName)
            );

            columnMap.forEach((name, data) -> staticBlock.append(
                    "bdr.insertColumn(\"%s\", new Column(SQLDataType.%s, MakeColumn.Config.%s.get()));".formatted(
                            name.replace("_", "").toLowerCase(),
                            data.datatype(),
                            data.config().name()
                    ))
            );

            staticBlock.append("table = bdr.build();");

            classBlock.append(staticBlock);
            classBlock.newLine();






            var sqlColumns = new CodeBlock.ArbitraryBlock(2);
            var queryColumns = new ArrayList<String>();
            columnMap.forEach((key, value) -> queryColumns.add("%s %s %s".formatted(
                key.replace("_", "").toLowerCase(),
                value.datatype().type,
                value.config().get().toString()
            )));

            for (int i = 0; i < queryColumns.size() - 1; i++) {
                sqlColumns.append(queryColumns.get(i) + ",");
            }
            sqlColumns.append(queryColumns.getLast());

            //Table maker
            classBlock.append(new Method(
                AccessModifiers.Scope.PUBLIC.getStatic(),
                strType,
                "tableMaker"
            ).append("return")
                .append(new CodeBlock.TextBlock(2)
                    .append("CREATE TABLE IF NOT EXISTS %s (".formatted(tableName))
                    .append(sqlColumns)
                    .append(") WITHOUT ROWID;")));

            //Single query
            var columnKeyMap = columnMap.entrySet().stream()
                .collect(Collectors.partitioningBy(
                    set -> set.getValue().config().get().PrimaryKey(),
                    Collectors.mapping(
                        set -> set.getKey().replace("_", "").toLowerCase(),
                        Collectors.toSet()
                    ))
                );

            classBlock.newLine();

            if (columnKeyMap.get(false).isEmpty()) {
                classBlock.append("//because there is no value that's not a key, single query is not generated");
            } else {
                out.useType(Pattern.class);

                classBlock.append(new Method(
                        AccessModifiers.Scope.PUBLIC.getStatic(),
                        strType,
                        "querySingle",
                        new Parameter("queryColumn", out.useType(Set.class, strType)),
                        new Parameter("keyValue", out.useType(Map.class, strType,  out.useType(SQLData.class)))
                    ).append("var statement = ")
                        .append(new CodeBlock.TextBlock(2)
                            .append("SELECT %s FROM %s".formatted(String.join(", ", columnKeyMap.get(false)), tableName))
                            .append("WHERE %s;".formatted(columnKeyMap.get(true).stream()
                                .map("kv@%s@"::formatted)
                                .collect(Collectors.joining(" AND "))
                            ))
                        ).append("return Pattern.compile(\"kv@(\\\\w+)@\")")
                        .append("    .matcher(statement).replaceAll(str -> str.group(1) + \" = \" + keyValue.get(str.group(1)).sqlForm());")
                );
            }

            classBlock.newLine();

            //Insert or replace
            classBlock.append(new Method(
                AccessModifiers.Scope.PUBLIC.getStatic(),
                strType,
                "insertOrReplace",
                new Parameter("columns", out.useType(Map.class, strType, out.useType(SQLData.class)))
            ).append("var str = ")
                .append(new CodeBlock.TextBlock(2)
                    .append("REPLACE INTO %s (@column_keys@)".formatted(tableName))
                    .append("VALUES(@column_values@)"))
                .append("var keys = columns.keySet().stream().toList();")
                .append("return str.replace(\"@column_keys@\", String.join(\", \", keys))")
                .append("    .replace(\"@column_values@\", String.join(\", \", keys.stream()")
                .append("        .map(v -> columns.get(v).sqlForm())")
                .append("        .collect(Collectors.joining(\", \")))) + ';';"));

            out.printPackage();
            out.printImport();
            out.printf(classBlock.toString());
        }
    }
}
