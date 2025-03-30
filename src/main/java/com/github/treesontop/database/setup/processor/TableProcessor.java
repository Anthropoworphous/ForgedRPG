package com.github.treesontop.database.setup.processor;

import com.github.treesontop.Util;
import com.github.treesontop.codeGenerator.CodeWriter;
import com.google.auto.service.AutoService;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile("com.github.treesontop.database.setup." + tableClassName);

        try (CodeWriter out = new CodeWriter(builderFile.openWriter())) {
            printStaticClassStart(out, tableClassName);

            out.println("    public static Table table;");
            out.println();

            out.println("    static {");
            out.printf("        var bdr = new Table.Builder(\"%s\");%n", tableName);

            columnMap.forEach((name, data) ->
                    out.printf("        bdr.insertColumn(\"%s\", new Column(SQLDataType.%s, MakeColumn.Config.%s.get()));%n",
                    name.replace("_", "").toLowerCase(),
                    data.datatype(),
                    data.config().name())
            );

            out.println("        table = bdr.build();");
            out.printf("    }%n%n");

//            printStaticFunctionStart(out, "String", "tableMaker");


            var cd = new CodeWriter.CodeBlock(1);
            var columns = columnMap.entrySet().stream().map(set ->
                    "%s %s %s".formatted(
                            set.getKey().replace("_", "").toLowerCase(),
                            set.getValue().datatype().type,
                            set.getValue().config().get().toString()
                    )
            ).collect(Collectors.joining(", "));

            cd.append("return \"CREATE TABLE IF NOT EXIST %s (%s) WITHOUT ROWID;\";".formatted(
                    tableName, columns
            ));
            var method = out.makePublicStaticMethod("tableMaker", out.useType(String.class), cd);
            out.printf("    " + method.toString());
        }
    }

    private void printStaticClassStart(PrintWriter out, String className) {
        out.println("package com.github.treesontop.database.setup;");
        out.println();
        out.println("import com.github.treesontop.database.Column;");
        out.println("import com.github.treesontop.database.SQLDataType;");
        out.println("import com.github.treesontop.database.Table;");
        out.println("import com.github.treesontop.database.setup.processor.MakeColumn;");
        out.println();

        out.printf("public class %s {%n", className);
    }

    private void printStaticFunctionStart(PrintWriter out, String returnType, String functionName, String... var) {
        out.printf("    public static %s %s(%s) {%n", returnType, functionName, String.join(", ", var));
    }
}