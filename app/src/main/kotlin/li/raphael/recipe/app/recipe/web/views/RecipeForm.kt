package li.raphael.recipe.app.recipe.web.views

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.data
import li.raphael.kotlinhtml.utils.dataAttributes
import li.raphael.recipe.app.components.renderPage
import li.raphael.recipe.app.recipe.Recipe
import li.raphael.recipe.app.shared.components.uButton
import li.raphael.recipe.app.shared.validation.validatedInput

fun renderRecipeForm() =
    renderPage(t("recipes.create.title")) {
        createRecipeForm()
    }

private fun FlowContent.createRecipeForm() {
    form(method = FormMethod.post) {
        classes += "usa-form"
        dataAttributes(
            "controller" to "form-validation",
            "form-validation-target" to "form",
        )
        attributes["novalidate"] = ""

        validatedInput(Recipe::title, {
            required()
            minLength(3)
        }, label = t("recipes.create.recipe.title"))

        uButton {
            type = ButtonType.submit
            data["form-validation-target"] = "button"
            +t("recipes.create.submit")
        }
    }
}
