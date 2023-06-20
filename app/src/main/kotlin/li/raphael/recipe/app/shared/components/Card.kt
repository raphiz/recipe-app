package li.raphael.recipe.app.shared.components

import kotlinx.html.*

fun FlowContent.uCard(
    id: String? = null,
    title: H2.() -> Unit,
    image: (FlowContent.() -> Unit)? = null,
    footer: (FlowContent.() -> Unit)? = null,
) {
    div("usa-card") {
        id?.let { this.id = id }
        div("usa-card__container") {
            div("usa-card__header") {
                h2("usa-card__heading") {
                    title()
                }
            }
            image?.let {
                div("usa-card__media") {
                    div("usa-card__img") {
                        image()
                    }
                }
            }

            footer?.let {
                div("usa-card__footer") {
                    footer()
                }
            }
        }
    }
}
