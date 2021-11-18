plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("org.jetbrains.dokka") version "1.5.31"
}

group = "de.nycode"
version = "0.10.0"

apply(from = "publishing.gradle.kts")

repositories {
    mavenCentral()
}

dependencies {
    api("com.squareup.retrofit2", "retrofit", "2.9.0")
    api("org.jetbrains.kotlinx", "kotlinx-serialization-core", "1.3.1")

    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine")

    testImplementation("com.squareup.okhttp3", "mockwebserver3", "5.0.0-alpha.2")
    testImplementation("com.squareup.okhttp3", "mockwebserver3-junit5", "5.0.0-alpha.2")
    testImplementation("org.jetbrains.kotlinx", "kotlinx-serialization-protobuf", "1.3.1")
    testImplementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.3.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

kotlin {
    explicitApi()
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
                "-Xjsr305=strict",
            )
        }
    }
    withType<Test> {
        useJUnitPlatform {
            systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
        }
    }
}
