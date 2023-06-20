package li.raphael.recipe.app.shared.components

import kotlinx.html.BODY
import kotlinx.html.a

fun BODY.uSkiplink(targetElementId: String, label: String) {
    a(classes = "usa-skipnav") {
        href = "#$targetElementId"
        +label
    }
}
