plugins {
    id("recipe-app.module")
    id("recipe-app.spring")
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-html-jvm:0.10.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
}
