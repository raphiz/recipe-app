package li.raphael.recipe.app.components

import kotlinx.html.FlowContent
import kotlinx.html.p
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.data
import li.raphael.recipe.app.shared.components.*

fun FlowContent.confirmDialogTemplate() {
    modal(
        id = "turbo-confirm",
        closeLabel = t("modal.close"),
        customizer = {
            data["controller"] = "confirm-modal"
        },
        title = {
            data["confirm-modal-target"] = "title"
        },
        main = {
            p {
                data["confirm-modal-target"] = "message"
            }
        },
        footer = {
            uButtonGroup(
                {
                    uButton(UButtonVariant.UNSTYLED) {
                        classes += "padding-105 text-center"
                        +t("modal.cancel")
                    }
                },
                {
                    uButton {
                        data["confirm-modal-target"] = "confirm"
                        +t("modal.confirm")
                    }
                },
            )
        },
    )
}
