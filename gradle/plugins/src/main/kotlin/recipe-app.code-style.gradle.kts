plugins {
    id("org.jmailen.kotlinter")
}

tasks.register("format") {
    dependsOn(tasks.named("formatKotlin"))
}
