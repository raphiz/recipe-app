package li.raphael.recipe.app.recipe.web.views

import kotlinx.html.*
import li.raphael.kotlinhtml.tags.TurboAction.REPLACE
import li.raphael.kotlinhtml.tags.turboFrame
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.dataAttributes
import li.raphael.recipe.app.RecipeRoutes
import li.raphael.recipe.app.Routes
import li.raphael.recipe.app.components.renderPage
import li.raphael.recipe.app.recipe.Recipe
import li.raphael.recipe.app.recipe.RecipeId
import li.raphael.recipe.app.shared.PagedList
import li.raphael.recipe.app.shared.components.*
import li.raphael.recipe.app.shared.components.IconType.DELETE
import li.raphael.recipe.app.shared.components.IconType.EDIT
import li.raphael.recipe.app.shared.components.UButtonVariant.UNSTYLED

const val recipeListId = "recipe-list"

fun renderRecipeList(recipes: PagedList<Recipe>, searchText: String?) =
    renderPage(t("recipes.list.title")) {
        recipeList(recipes, searchText)
    }

private fun FlowContent.recipeList(recipes: PagedList<Recipe>, searchText: String?) {
    uSearchForm(fieldName = RecipeRoutes.Params.searchFieldName, searchText = searchText) {
        attributes["data-turbo-frame"] = recipeListId
    }
    renderPagedCardList(recipes, searchText)
}

fun FlowContent.renderPagedCardList(recipes: PagedList<Recipe>, searchText: String?) {
    turboFrame(recipeListId, turboAction = REPLACE) {
        uCardGroup(
            recipes.map { recipe ->
                recipe.id.domId() to { recipeCard(recipe) }
            },
        )
        uPagination(recipes) { page ->
            Routes.recipes.list(searchText, page)
        }
    }
}

fun FlowContent.recipeCard(recipe: Recipe) {
    uCard(
        title = {
            if (recipe.source != null) a(href = recipe.source.toString()) { +recipe.title } else +recipe.title
            editRecipeButton(recipe)
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
            deleteRecipeButton(recipe)
        },
    )
}

private fun FlowContent.deleteRecipeButton(recipe: Recipe) {
    form(action = Routes.recipes.delete(recipe.id), method = FormMethod.post) {
        dataAttributes(
            "controller" to "submit-with-query-params",
        )
        uButton(UNSTYLED) {
            dataAttributes(
                "turbo-confirm" to t("recipes.delete.confirm.message", recipe.title),
                "turbo-confirm-title" to t("recipes.delete.confirm.title", recipe.title),
                "turbo-confirm-button-style" to UButtonVariant.SECONDARY.classNames,
                "turbo-frame" to "_top",
            )
            uIcon(DELETE)
            uScreenReaderOnly { +t("recipes.delete.confirm.title", recipe.title) }
        }
    }
}

private fun FlowContent.editRecipeButton(recipe: Recipe) {
    form(action = Routes.recipes.edit(recipe.id), method = FormMethod.get) {
        style = "display: inline-block"
        uButton(UNSTYLED) {
            uIcon(EDIT)
            uScreenReaderOnly { +t("recipes.edit.title", recipe.title) }
        }
    }
}

fun RecipeId.domId() = "recipe-$value"
