plugins {
    id("java")
}

group = "com.github.treesontop"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:0366b58bfe")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:2.1.0-alpha1")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("org.beryx:text-io:3.4.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.13.0-M2")
    testImplementation("org.mockito:mockito-core:5.16.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.16.1")

    // Annotation processor dependencies
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")

    // If you're using your own annotation processors
    annotationProcessor(files(layout.buildDirectory.dir("classes/java/main").get().asFile))
}

// Make sure the annotation processor can find the compiled classes
sourceSets {
    main {
        java {
            srcDir(layout.buildDirectory.dir("generated/sources/annotations").get().asFile)
        }
    }
}

tasks.test {
    useJUnitPlatform()
    useTestNG()
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
