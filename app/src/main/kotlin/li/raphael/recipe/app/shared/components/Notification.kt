package li.raphael.recipe.app.shared.components

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id
import li.raphael.kotlinhtml.utils.data

const val NOTIFICATION_AREA = "notifications"

fun FlowContent.uNotificationArea() {
    div("grid-container") {
        id = NOTIFICATION_AREA
    }
}

fun FlowContent.uNotification(text: String) {
    div("usa-alert usa-alert--success usa-alert--slim") {
        data["turbo-temporary"] = "true"
        div("usa-alert__body") {
            +text
        }
    }
}
