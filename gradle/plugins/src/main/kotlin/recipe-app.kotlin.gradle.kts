import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

val javaVersion = JavaLanguageVersion.of("17")

java {
    toolchain {
        languageVersion.set(javaVersion)
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
        jvmTarget.set(JvmTarget.fromTarget(javaVersion.toString()))
        languageVersion.set(KotlinVersion.KOTLIN_1_8)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Configuration Cache workaround for launching main via intellij
// https://github.com/gradle/gradle/issues/21364
tasks.withType<JavaExec> {
    if (name.endsWith("main()")) {
        notCompatibleWithConfigurationCache("JavaExec created by IntelliJ")
    }
}
