plugins {
    id("recipe-app.module")
    id("recipe-app.web-module")
    id("recipe-app.spring-boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation(projects.kotlinHtmlViews)

    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
}

importmap {
    download("@hotwired/stimulus", "3.2.1")
    download("@hotwired/turbo", "7.3.0")
}

assets {
    download(
        "https://ga.jspm.io/npm:es-module-shims@1.7.1/dist/es-module-shims.js",
        "es-module-shims.js",
    )
    download(
        "https://ga.jspm.io/npm:uswds@2.14.0/dist/css/uswds.css",
        "uswds.css",
    )
    download(
        "https://ga.jspm.io/npm:uswds@2.14.0/dist/img/sprite.svg",
        "img/sprite.svg",
    )
    download(
        "https://ga.jspm.io/npm:uswds@2.14.0/dist/img/usa-icons/check_circle.svg",
        "img/usa-icons/check_circle.svg",
    )
    download(
        "https://ga.jspm.io/npm:uswds@2.14.0/dist/img/usa-icons-bg/search--white.svg",
        "img/usa-icons-bg/search--white.svg",
    )
    setOf("regular", "bold", "italic").forEach { variant ->
        setOf("woff", "woff2").forEach { extension ->
            download(
                "https://ga.jspm.io/npm:uswds@2.14.0/dist/fonts/source-sans-pro/sourcesanspro-$variant-webfont.$extension",
                "fonts/source-sans-pro/sourcesanspro-$variant-webfont.$extension",
            )
        }
    }

    setOf("woff", "woff2").forEach { extension ->
        download(
            "https://ga.jspm.io/npm:uswds@2.14.0/dist/fonts/merriweather/Latin-Merriweather-Bold.$extension",
            "fonts/merriweather/Latin-Merriweather-Bold.$extension",
        )
    }
}

tasks.register("run").configure {
    dependsOn(tasks.bootRun)
}
