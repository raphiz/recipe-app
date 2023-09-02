pluginManagement {
    repositories {
        System.getenv("MAVEN_SOURCE_REPOSITORY")?.let {
            maven(it) {
                metadataSources {
                    gradleMetadata()
                    mavenPom()
                    artifact()
                }
            }
        } ?: gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        System.getenv("MAVEN_SOURCE_REPOSITORY")?.let {
            maven(it) {
                metadataSources {
                    gradleMetadata()
                    mavenPom()
                    artifact()
                }
            }
        } ?: gradlePluginPortal()
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}
