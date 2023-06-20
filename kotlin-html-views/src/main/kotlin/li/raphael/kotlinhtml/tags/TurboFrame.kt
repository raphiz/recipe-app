package li.raphael.kotlinhtml.tags

import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.HtmlTagMarker
import kotlinx.html.TagConsumer
import kotlinx.html.visit
import kotlinx.html.visitAndFinalize

/**
 * Definition of a custom element for turbo.
 * See https://turbo.hotwired.dev/handbook/frames
 */
class TurboFrame(id: String, consumer: TagConsumer<*>) :
    HTMLTag(
        "turbo-frame",
        consumer,
        mapOf("id" to id),
        inlineTag = true,
        emptyTag = false,
    ),
    HtmlBlockTag

@HtmlTagMarker
inline fun FlowContent.turboFrame(id: String, crossinline block: TurboFrame.() -> Unit = {}) {
    TurboFrame(id, consumer).visit(block)
}

@HtmlTagMarker
inline fun <T, C : TagConsumer<T>> C.turboFrame(id: String, crossinline block: TurboFrame.() -> Unit): T =
    TurboFrame(id, this).visitAndFinalize(this, block)
