package li.raphael.kotlinhtml.tags
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.HtmlTagMarker
import kotlinx.html.TagConsumer
import kotlinx.html.visitAndFinalize

/**
 * Definition of a custom element for turbo.
 * See https://turbo.hotwired.dev/handbook/streams
 */
class TurboStream(
    action: StreamAction,
    target: String,
    consumer: TagConsumer<*>,
) : HTMLTag(
    "turbo-stream",
    consumer,
    mapOf("action" to action.value, "target" to target),
    inlineTag = true,
    emptyTag = false,
),
    HtmlBlockTag

@HtmlTagMarker
inline fun <T, C : TagConsumer<T>> C.turboStream(
    action: StreamAction,
    target: String,
    crossinline block: Template.() -> Unit = {},
): T = TurboStream(action, target, this).visitAndFinalize(this) { template(block = block) }

enum class StreamAction(val value: String) {
    APPEND("append"),
    PREPEND("prepend"),
    REPLACE("replace"),
    UPDATE("update"),
    REMOVE("remove"),
    BEFORE("before"),
    AFTER("after"),
}
