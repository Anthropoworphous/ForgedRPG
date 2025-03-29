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
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
}