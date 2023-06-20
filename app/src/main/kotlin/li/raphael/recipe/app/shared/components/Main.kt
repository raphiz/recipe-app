package li.raphael.recipe.app.shared.components

import kotlinx.html.*

fun BODY.uMain(id: String, title: String, content: FlowContent.() -> Unit) {
    div {
        uNotificationArea()
    }
    div {
        div("grid-container") {
            main {
                this.id = id
                h1 { +title }
                content(this)
            }
        }
    }
}
