package li.raphael.kotlinhtml

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.springframework.util.ClassUtils
import org.springframework.web.servlet.resource.ResourceUrlProvider

class ImportMap(
    private val resourceUrlProvider: ResourceUrlProvider,
    private val objectMapper: ObjectMapper,
    private val mappings: MutableMap<String, String> = mutableMapOf(),
) : Map<String, String> by mappings {

    init {
        addImportmapFromClasspath(objectMapper)
    }

    private fun addImportmapFromClasspath(objectMapper: ObjectMapper) {
        ClassUtils.getDefaultClassLoader()!!.getResourceAsStream("importmap.json")
            ?.let { importMapOnClasspath ->
                objectMapper.readTree(importMapOnClasspath)["imports"]
                    .fields().forEach { (modulePath, url) ->
                        putContentMapping(modulePath, url.asText())
                    }
            }
    }

    fun putContentMapping(modulePath: String, url: String) {
        this.mappings[modulePath] = resourceUrlProvider.getForLookupPath(url) ?: url
    }

    val jsonValue: String
        get() {
            val imports = objectMapper.createObjectNode() as ObjectNode
            mappings.map { (identifier, path) ->
                imports.put(identifier, path)
            }

            val root = objectMapper.createObjectNode() as ObjectNode
            root.set<ObjectNode>("imports", imports)
            return root.toPrettyString()
        }
}
