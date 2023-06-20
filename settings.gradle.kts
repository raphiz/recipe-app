rootProject.name = "recipe-app"

pluginManagement {
    includeBuild("gradle/plugins")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("com.gradle.enterprise") version ("3.13")
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
