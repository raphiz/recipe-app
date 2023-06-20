package li.raphael.recipe.app.shared.validation

import kotlinx.html.INPUT
import li.raphael.kotlinhtml.templatecontext.t

class Validation(val message: String, val modifyInput: (input: INPUT) -> Unit = {})
class ValidationContext {
    val validators = mutableMapOf<String, Validation>()
    val required
        get() = validators.containsKey("required")

    fun required() {
        validators["required"] = Validation(t("validator.required")) { it.required = true }
    }

    fun minLength(min: Int) {
        validators["minLength"] = Validation(t("validator.minLength", min)) { it.minLength = "$min" }
    }
}
