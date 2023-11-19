package li.raphael.recipe.app.recipe.web.views

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.data
import li.raphael.kotlinhtml.utils.dataAttributes
import li.raphael.recipe.app.RecipeRoutes
import li.raphael.recipe.app.Routes
import li.raphael.recipe.app.components.renderPage
import li.raphael.recipe.app.recipe.Recipe
import li.raphael.recipe.app.shared.PAGE_OFFSET
import li.raphael.recipe.app.shared.PAGE_SIZE
import li.raphael.recipe.app.shared.Page
import li.raphael.recipe.app.shared.components.uButton
import li.raphael.recipe.app.shared.components.uCard
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
            data["turbo-submits-with"] = t("recipes.create.in-progress")
            +t("recipes.create.submit")
        }
    }
}

fun FlowContent.editRecipeCard(recipe: Recipe, currentPage: Page, searchText: String?) {
    form(method = FormMethod.post, action = Routes.recipes.edit(recipe.id)) {
        classes += "usa-form"
        dataAttributes(
            "controller" to "form-validation",
            "form-validation-target" to "form",
        )
        attributes["novalidate"] = ""
        hiddenInput(name = PAGE_SIZE) { value = "${currentPage.size}" }
        hiddenInput(name = PAGE_OFFSET) { value = "${currentPage.offset}" }
        hiddenInput(name = RecipeRoutes.Params.searchFieldName) { value = searchText ?: "" }
        uCard(
            title = {
                validatedInput(
                    Recipe::title,
                    {
                        required()
                        minLength(3)
                    },
                    label = t("recipes.create.recipe.title"),
                    fieldValue = recipe.title,
                )
            },
            image = {
                img {
                    src = recipe.image.toString()
                    alt = t("recipes.list.image.alt-text", recipe.title)
                    width = "800"
                    height = "800"
                }
            },
            footer = {
                uButton {
                    type = ButtonType.submit
                    data["form-validation-target"] = "button"
                    data["turbo-submits-with"] = t("recipes.update.in-progress")
                    +t("recipes.update.submit")
                }
            },
        )
    }
}
