plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

tasks.validatePlugins.configure {
    enableStricterValidation.set(true)
}

dependencies {
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin:1.8.21")
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.8.21")

    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin:3.1.0")

    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.18.0")
}
