package com.github.treesontop.database.setup.processor;

import com.github.treesontop.codeGenerator.*;
import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.Table;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            var classBlock = new ClassBlock(AccessModifiers.Scope.PUBLIC.get(), tableClassName, 0);

            classBlock.append(new Field(
                    AccessModifiers.Scope.PUBLIC.getStatic(),
                    new Parameter("table", out.useType(Table.class)))
            );
            classBlock.newLine();

            out.useType(Table.class);
            out.useType(Column.class);
            out.useType(SQLDataType.class);
            out.useType(MakeColumn.class);

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

            var sqlColumns = new CodeBlock.ArbitraryBlock(4);
            columnMap.forEach((key, value) ->
                    sqlColumns.append("%s %s %s".formatted(
                            key.replace("_", "").toLowerCase(),
                            value.datatype().type,
                            value.config().get().toString()
                    ))
            );

            classBlock.append(new Method(
                    new AccessModifiers(AccessModifiers.Scope.PUBLIC, true, false),
                    out.useType(String.class),
                    "tableMaker"
            ).append("return")
                    .append(new CodeBlock.TextBlock(2)
                            .append(new CodeBlock.ArbitraryBlock(3)
                                    .append("CREATE TABLE IF NOT EXIST %s (".formatted(tableName))
                                    .append(sqlColumns)
                                    .append(") WITHOUT ROWID;"))));

            out.printPackage();
            out.printImport();
            out.printf(classBlock.toString());
        }
    }
}