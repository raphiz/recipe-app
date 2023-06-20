plugins {
    id("recipe-app.kotlin")
    kotlin("plugin.spring")
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
}
