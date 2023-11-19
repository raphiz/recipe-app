package li.raphael.recipe.app.shared.validation

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.data
import kotlin.reflect.KProperty1

fun FlowContent.validatedInput(
    property: KProperty1<*, String>,
    block: ValidationContext.() -> Unit,
    validationController: String = "validation",
    label: String,
    fieldValue: String = "",
) {
    val fieldId = property.name

    val validationContext = ValidationContext()
    validationContext.block()

    label {
        data["controller"] = validationController
        htmlFor = fieldId

        +label
        if (validationContext.required) {
            abbr("usa-hint usa-hint--required") {
                title = t("form.required")
                +"*"
            }
        }
        div {
            data["validation-target"] = "control"
            input(classes = "usa-input") {
                id = fieldId
                type = InputType.text
                name = fieldId
                required = validationContext.required
                value = fieldValue
                validationContext.validators.forEach { (_, validator) -> validator.modifyInput(this) }
                data["validation-target"] = "input"
            }
            validationContext.validators.forEach { (key, validator) ->
                p("usa-error-message") {
                    id = "$fieldId-error-$key"
                    classes += "display-none"
                    data["validation-target"] = "error"
                    data["error-key"] = key
                    +validator.message
                }
            }
        }
    }
}
