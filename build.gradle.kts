plugins {
    kotlin("jvm") version "2.0.10"
    id("io.ktor.plugin") version "2.3.12"
    kotlin("plugin.serialization") version "1.8.0"
    application
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.12")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("io.ktor:ktor-server-status-pages:2.3.12")
    implementation("io.insert-koin:koin-core:3.4.0")
    implementation("io.insert-koin:koin-ktor:3.4.0")
    implementation("ch.qos.logback:logback-classic:1.5.7")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("org.jetbrains.exposed:exposed-core:0.38.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.1")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
}
