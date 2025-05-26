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
    implementation("net.kyori:adventure-text-serializer-plain:4.20.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.13.0-M2")
    testImplementation("org.mockito:mockito-core:5.16.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.16.1")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Test>("test") {
    jvmArgs("-XX:+EnableDynamicAgentLoading")
    useJUnitPlatform()
}
