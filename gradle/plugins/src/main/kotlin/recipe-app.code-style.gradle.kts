import com.diffplug.gradle.spotless.KotlinExtension
import com.diffplug.spotless.LineEnding

plugins {
    id("com.diffplug.spotless")
}

spotless {
    val disabledKtlintRules = mapOf(
        "ktlint_standard_no-wildcard-imports" to "disabled",
        "ktlint_standard_import-ordering" to "disabled",
    )

    kotlin {
        ktlint().editorConfigOverride(disabledKtlintRules)
    }

    format("gradle", KotlinExtension::class.java) {
        target("*.kts")
        ktlint().editorConfigOverride(disabledKtlintRules)
    }

    // Workaround for https://github.com/diffplug/spotless/issues/1644
    lineEndings = LineEnding.UNIX
}

tasks.register("format") {
    dependsOn(tasks.spotlessApply)
}
