package li.raphael.kotlinhtml.tags

import kotlinx.html.*
import li.raphael.kotlinhtml.utils.filterValuesNotNull

/**
 * Definition of a custom element for turbo.
 * See https://turbo.hotwired.dev/handbook/frames
 */
class TurboFrame(id: String, turboAction: TurboAction?, consumer: TagConsumer<*>) :
    HTMLTag(
        "turbo-frame",
        consumer,
        mapOf("id" to id, "data-turbo-action" to turboAction?.value).filterValuesNotNull(),
        inlineTag = true,
        emptyTag = false,
    ),
    HtmlBlockTag

@HtmlTagMarker
inline fun FlowContent.turboFrame(id: String, turboAction: TurboAction? = null, crossinline block: TurboFrame.() -> Unit = {}) {
    TurboFrame(id, turboAction, consumer).visit(block)
}

@HtmlTagMarker
inline fun <T, C : TagConsumer<T>> C.turboFrame(id: String, turboAction: TurboAction? = null, crossinline block: TurboFrame.() -> Unit): T =
    TurboFrame(id, turboAction, this).visitAndFinalize(this, block)

enum class TurboAction(val value: String) {
    ADVANCE("advance"),
    REPLACE("replace"),
}
