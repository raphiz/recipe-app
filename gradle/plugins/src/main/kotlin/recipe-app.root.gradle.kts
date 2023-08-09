// Configure the ':tasks' task of the root project to only show
// the main lifecycle tasks as entry points to the build
val mainBuildGroup = "main build"
tasks.named<TaskReportTask>("tasks") {
    displayGroup = mainBuildGroup
}

tasks.register("build") {
    group = mainBuildGroup
    description = "Complete build of all modules and the application"
    dependsOn(subprojects.map { ":${it.name}:$name" })
}

tasks.register("classes") {
    group = mainBuildGroup
    description = "Assembles main classes. Useful for hot reloading"
    dependsOn(subprojects.map { ":${it.name}:$name" })
}

tasks.register("format") {
    group = mainBuildGroup
    description = "Format all project files"
    dependsOn(subprojects.map { ":${it.name}:$name" })
}

tasks.register("check") {
    group = mainBuildGroup
    description = "Runs all checks"
    dependsOn(subprojects.map { ":${it.name}:$name" })
}

tasks.register("assemble") {
    group = mainBuildGroup
    description = "Build standalone application without any checks (tests)"
    dependsOn(subprojects.map { ":app:$name" })
}

tasks.register("run") {
    group = mainBuildGroup
    description = "Build and run as standalone application"
    dependsOn(":app:$name")
}
