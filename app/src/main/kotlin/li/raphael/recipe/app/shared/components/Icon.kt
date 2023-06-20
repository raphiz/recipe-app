package li.raphael.recipe.app.shared.components

import kotlinx.html.*

fun FlowOrPhrasingContent.uIcon(type: IconType) {
    svg("usa-icon") {
        attributes["aria-hidden"] = "true"
        attributes["focusable"] = "false"
        role = "img"
        use(href = "/img/sprite.svg#${type.xHref}")
    }
}

enum class IconType(val xHref: String) {
    CLOSE("close"),
    DELETE("delete"),
    NAVIGATE_BEFORE("navigate_before"),
    NAVIGATE_NEXT("navigate_next"),
    SEARCH("search"),
}

private class USE(href: String, consumer: TagConsumer<*>) :
    HTMLTag(
        "use",
        consumer,
        mapOf("xlink:href" to href),
        inlineTag = true,
        emptyTag = false,
    ),
    HtmlBlockTag

@HtmlTagMarker
private inline fun SVG.use(href: String, crossinline block: USE.() -> Unit = {}) {
    USE(href, consumer).visit(block)
}
