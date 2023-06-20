package li.raphael.recipe.app.shared.components

import kotlinx.html.FlowContent
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.ul

fun FlowContent.uCardGroup(cards: List<Pair<String, FlowContent.() -> Unit>>) {
    ul("usa-card-group") {
        cards.forEach { (id, card) ->
            li(" desktop:grid-col-3 tablet:grid-col-4 grid-col-12") {
                this.id = id
                card()
            }
        }
    }
}
