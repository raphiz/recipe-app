plugins {
    `kotlin-dsl`
}

tasks.validatePlugins.configure {
    enableStricterValidation.set(true)
}

dependencies {
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin:1.9.21")
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.9.21")

    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin:3.2.0")

    implementation("org.jmailen.gradle:kotlinter-gradle:3.16.0")
}
