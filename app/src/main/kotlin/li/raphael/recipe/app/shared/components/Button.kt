package li.raphael.recipe.app.shared.components

import kotlinx.html.BUTTON
import kotlinx.html.FlowContent
import kotlinx.html.attributes.Attribute
import kotlinx.html.attributes.StringSetAttribute
import kotlinx.html.button

fun FlowContent.uButton(variant: UButtonVariant? = null, label: String? = null, block: BUTTON.() -> Unit = {}) {
    button {
        uRawButton(variant) {
            block()
            if (label != null) +label
        }
    }
}

fun <T : FlowContent> T.uRawButton(variant: UButtonVariant? = null, block: T.() -> Unit = {}) {
    classes += setOfNotNull("usa-button", variant?.classNames)
    block()
}

var FlowContent.classes: Set<String>
    get() = attributeSetStringStringSet.get(this, "class")
    set(newValue) {
        attributeSetStringStringSet.set(this, "class", newValue)
    }

internal val attributeSetStringStringSet: Attribute<Set<String>> = StringSetAttribute()

enum class UButtonVariant(val classNames: String) {
    PRIMARY("usa-button--primary"),
    UNSTYLED("usa-button--unstyled"),
    SECONDARY("usa-button--secondary"),
}
