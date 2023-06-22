rootProject.name = "recipe-app"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle/plugins")
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
        } ?: mavenCentral()
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

plugins {
    id("com.gradle.enterprise") version ("3.16")
}

gradleEnterprise {
    if (System.getenv("CI") != null) {
        buildScan {
            publishAlways()
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
        }
    }
}

include("app")
include("kotlin-html-views")
