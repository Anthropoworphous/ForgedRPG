plugins {
    id("java")
}

group = "com.github.treesontop"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:f71ab6d851")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")

    // Annotation processor dependencies
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")

    // If you're using your own annotation processors
    annotationProcessor(files(layout.buildDirectory.dir("classes/java/main").get().asFile))
}

// Add a separate task for annotation processing
tasks.register<JavaCompile>("processAnnotations") {
    source = sourceSets.main.get().java.sourceDirectories.asFileTree
    classpath = configurations.compileClasspath.get()
    destinationDirectory.set(layout.buildDirectory.dir("generated/sources/annotations"))

    // Use Java 21
    sourceCompatibility = "21"
    targetCompatibility = "21"

    options.annotationProcessorPath = configurations.annotationProcessor.get()
    options.compilerArgs.add("-proc:only")  // Only process annotations
}

// Make the compile task depend on the annotation processing
tasks.compileJava {
    dependsOn("processAnnotations")
    source(fileTree(layout.buildDirectory.dir("generated/sources/annotations").get().asFile))
}

// Make sure the annotation processor can find the compiled classes
sourceSets {
    main {
        java {
            srcDir(layout.buildDirectory.dir("generated/sources/annotations").get().asFile)
        }
    }
}