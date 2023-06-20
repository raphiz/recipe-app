package li.raphael.recipe.app.shared.components

import kotlinx.html.*

fun FlowContent.modal(id: String, title: H2.() -> Unit, main: FlowContent.() -> Unit, footer: FlowContent.() -> Unit, customizer: DIALOG.() -> Unit = {}, closeLabel: String) {
    dialog("usa-modal-wrapper is-hidden") {
        this.id = id
        customizer()
        form {
            attributes["method"] = "dialog"
            div("usa-modal__content") {
                div("usa-modal__main") {
                    h2("usa-modal__heading", title)
                    div("usa-prose", main)
                    div("usa-modal__footer", footer)
                }
                uButton {
                    classes += "usa-modal__close"
                    attributes["aria-label"] = closeLabel
                    uIcon(IconType.CLOSE)
                }
            }
        }
    }
}
