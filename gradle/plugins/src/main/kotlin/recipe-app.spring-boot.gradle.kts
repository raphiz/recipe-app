plugins {
    id("recipe-app.spring")
    id("org.springframework.boot")
}

dependencies {
    "developmentOnly"(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
}

tasks.bootRun.configure {
    // Configure the bootRun task to use the unprocessed resources directly to prevents full restarts when assets change.
    sourceResources(sourceSets["main"])
}
