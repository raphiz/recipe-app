package li.raphael.kotlinhtml.stimulus

class StimulusController(
    val identifier: String,
    val modulePath: String,
    val url: String,
    val controllerClassName: String,
) {
    companion object {
        private val pattern = "((.*)_controller)\\.m*js".toRegex()

        fun of(relativePath: String): StimulusController {
            val identifier = moduleIdentifierFromRelativePath(relativePath)
            return StimulusController(
                identifier = identifier,
                modulePath = modulePathFromRelativePath(relativePath),
                url = relativePath,
                controllerClassName = controllerClassNameFromIdentifier(identifier),
            )
        }

        private fun controllerClassNameFromIdentifier(identifier: String): String {
            val pattern = "-[a-z]".toRegex()
            return identifier.replace(pattern) { it.value.last().uppercase() }
                .replaceFirstChar { it.uppercase() }
                .replace("-", "_") + "Controller"
        }

        private fun modulePathFromRelativePath(relativePath: String) =
            relativePath.removePrefix("/").replace(pattern, "$1")

        private fun moduleIdentifierFromRelativePath(relativePath: String): String {
            val parts = relativePath.removePrefix("/controllers/").split("/")
            val prefix = parts.dropLast(1).joinToString("") { "$it--" }
            return prefix + pattern.matchEntire(parts.last())!!.groupValues[2].replace("_", "-")
        }
    }
}
