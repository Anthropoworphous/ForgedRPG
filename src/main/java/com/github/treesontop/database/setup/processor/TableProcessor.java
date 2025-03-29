package com.github.treesontop.database.setup.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("com.github.treesontop.database.setup.marker.MakeColumn")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class TableProcessor extends AbstractProcessor {
    public TableProcessor() {}

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton("com.github.treesontop.database.setup.marker.MakeColumn");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_21;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            if (elements.isEmpty()) { continue; }

            var className = elements.stream().findAny().get().getEnclosingElement().getSimpleName().toString();

            var columnMap = new HashMap<String, MakeColumn>();
            for(var e : elements) {
                columnMap.put(e.getSimpleName().toString(), e.getAnnotation(MakeColumn.class));
            }

            try {
                writeTableFile(className, columnMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return false;
    }

    private void writeTableFile(
            String className, Map<String, MakeColumn> columnMap)
            throws IOException {

        int lastDot = className.lastIndexOf('.');

        String tableClassName = className + "Table";

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile("com.github.treesontop.database.setup." + tableClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            out.println("package com.github.treesontop.database.setup;");
            out.println();

            out.print("public class ");
            out.print(tableClassName);
            out.println(" {");
            out.println();

            out.println("    public String tableMaker() {");
            out.print("        return \"CREATE TABLE IF NOT EXIST ");
            out.print(className);
            out.print("(");

            out.print(columnMap.entrySet().stream().map(set -> {
                var str = new StringBuilder();
                str.append(set.getKey().replace("_", ""));
                str.append(" ");
                str.append(set.getValue().datatype().type);
                str.append(" ");
                str.append(set.getValue().config().get().toString());
                return str.toString();
            }).collect(Collectors.joining(",")));

            out.println(") WITHOUT ROWID;");

            out.println("    }");

            out.println("}");
        }
    }
}
