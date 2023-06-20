package li.raphael.recipe.app.shared.components

import kotlinx.html.FlowContent
import kotlinx.html.LI
import kotlinx.html.li
import kotlinx.html.ul

fun FlowContent.uButtonGroup(vararg buttons: LI.() -> Unit) {
    ul("usa-button-group") {
        buttons.forEach {
            li("usa-button-group__item") {
                it()
            }
        }
    }
}
