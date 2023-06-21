package li.raphael.recipe.app.recipe.web.views

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.dataAttributes
import li.raphael.recipe.app.RecipeRoutes
import li.raphael.recipe.app.Routes
import li.raphael.recipe.app.components.renderPage
import li.raphael.recipe.app.recipe.Recipe
import li.raphael.recipe.app.recipe.RecipeId
import li.raphael.recipe.app.shared.*
import li.raphael.recipe.app.shared.components.*
import li.raphael.recipe.app.shared.components.IconType.*
import li.raphael.recipe.app.shared.components.UButtonVariant.UNSTYLED

fun renderRecipeList(recipes: PagedList<Recipe>, searchText: String?) =
    renderPage(t("recipes.list.title")) {
        recipeList(recipes, searchText)
    }

private fun FlowContent.recipeList(recipes: PagedList<Recipe>, searchText: String?) {
    uSearchForm(fieldName = RecipeRoutes.Params.searchFieldName, searchText = searchText)
    renderPagedCardList(recipes, searchText)
}

fun FlowContent.renderPagedCardList(recipes: PagedList<Recipe>, searchText: String?) {
    uCardGroup(
        recipes.map { recipe ->
            recipe.id.domId() to { recipeCard(recipe, recipes.page, searchText) }
        },
    )
    uPagination(recipes) { page ->
        Routes.recipes.list(searchText, page)
    }
}

private fun FlowContent.recipeCard(recipe: Recipe, currentPage: Page, searchText: String?) {
    uCard(
        title = {
            if (recipe.source != null) a(href = recipe.source.toString()) { +recipe.title } else +recipe.title
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
            deleteRecipeButton(recipe, currentPage, searchText)
        },
    )
}

private fun FlowContent.deleteRecipeButton(recipe: Recipe, currentPage: Page, searchText: String?) {
    form(action = Routes.recipes.delete(recipe.id), method = FormMethod.post) {
        hiddenInput(name = PAGE_SIZE) { value = "${currentPage.size}" }
        hiddenInput(name = PAGE_OFFSET) { value = "${currentPage.offset}" }
        hiddenInput(name = RecipeRoutes.Params.searchFieldName) { value = searchText ?: "" }
        uButton(UNSTYLED) {
            dataAttributes(
                "turbo-confirm" to t("recipes.delete.confirm.message", recipe.title),
            )
            uIcon(DELETE)
            uScreenReaderOnly { +t("recipes.delete.confirm.title", recipe.title) }
        }
    }
}

fun RecipeId.domId() = "recipe-$value"
