package li.raphael.kotlinhtml.stimulus

import li.raphael.kotlinhtml.ImportMap
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.FileNotFoundException

class StimulusControllerRegistry(
    private val importMap: ImportMap,
    private val webProperties: WebProperties,
) {
    init {
        loadAndRegisterStimulusControllers()
    }

    val stimulusControllers: List<StimulusController>
        get() = loadAndRegisterStimulusControllers()

    private fun loadAndRegisterStimulusControllers(): List<StimulusController> {
        val resolver = PathMatchingResourcePatternResolver()
        return webProperties.resources.staticLocations.map { resourceLocation ->
            val resourceLocationBaseUri =
                resolver.getResourceUriIfExists(resourceLocation) ?: return@map emptyList()
            resolver.getResources("${resourceLocation}controllers/**/*.mjs")
                .map { resource ->
                    val relativePath =
                        "/" + resource.uri.toString().substring(resourceLocationBaseUri.length)
                    StimulusController.of(relativePath).also {
                        importMap.putContentMapping(it.modulePath, it.url)
                    }
                }
        }.flatten()
    }

    fun autoLoadControllersJs(): String {
        val builder = StringBuilder()
        builder.appendLine(
            """
            import {Application} from "@hotwired/stimulus"
            window.Stimulus = Application.start()    
            """.trimIndent(),
        )
        stimulusControllers.forEach {
            builder.appendLine(
                """
                    import ${it.controllerClassName} from "${it.modulePath}"
                    Stimulus.register("${it.identifier}", ${it.controllerClassName})
                """.trimIndent(),
            )
        }
        return builder.toString()
    }

    private fun PathMatchingResourcePatternResolver.getResourceUriIfExists(resourceLocation: String): String? {
        return try {
            getResource(resourceLocation).uri.toString()
        } catch (e: FileNotFoundException) {
            null
        }
    }
}
