package li.raphael.recipe.app.shared.components

import kotlinx.html.FlowContent
import kotlinx.html.SPAN
import kotlinx.html.span

fun FlowContent.uScreenReaderOnly(block: SPAN.() -> Unit) {
    span("usa-sr-only", block)
}
