package li.raphael.recipe.app.shared.components

import kotlinx.html.FlowContent
import kotlinx.html.div
import li.raphael.kotlinhtml.tags.turboFrame

fun FlowContent.uCardGroup(cards: List<Pair<String, FlowContent.() -> Unit>>) {
    div("usa-card-group") {
        cards.forEach { (id, card) ->
            cardGroupItem(id, card)
        }
    }
}

fun FlowContent.cardGroupItem(id: String, card: FlowContent.() -> Unit) {
    turboFrame(id) {
        classes = setOf("desktop:grid-col-3 tablet:grid-col-4 grid-col-12")
        card()
    }
}
