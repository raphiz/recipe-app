package li.raphael.recipe.app.recipe.web.views

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.recipe.app.Routes
import li.raphael.recipe.app.components.renderPage
import li.raphael.recipe.app.recipe.Recipe
import li.raphael.recipe.app.recipe.RecipeId
import li.raphael.recipe.app.shared.*
import li.raphael.recipe.app.shared.components.*
import li.raphael.recipe.app.shared.components.IconType.*
import li.raphael.recipe.app.shared.components.UButtonVariant.UNSTYLED

fun renderRecipeList(recipes: PagedList<Recipe>) =
    renderPage(t("recipes.list.title")) {
        recipeList(recipes)
    }

private fun FlowContent.recipeList(recipes: PagedList<Recipe>) {
    // TODO: Add search form here
    renderPagedCardList(recipes)
}

fun FlowContent.renderPagedCardList(recipes: PagedList<Recipe>) {
    uCardGroup(
        recipes.map { recipe ->
            recipe.id.domId() to { recipeCard(recipe, recipes.page) }
        },
    )
    uPagination(recipes) { page ->
        Routes.recipes.list(page)
    }
}

private fun FlowContent.recipeCard(recipe: Recipe, currentPage: Page) {
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
            deleteRecipeButton(recipe, currentPage)
        },
    )
}

private fun FlowContent.deleteRecipeButton(recipe: Recipe, currentPage: Page) {
    form(action = Routes.recipes.delete(recipe.id), method = FormMethod.post) {
        hiddenInput(name = PAGE_SIZE) { value = "${currentPage.size}" }
        hiddenInput(name = PAGE_OFFSET) { value = "${currentPage.offset}" }
        uButton(UNSTYLED) {
            uIcon(DELETE)
            uScreenReaderOnly { +t("recipes.delete.confirm.title", recipe.title) }
        }
    }
}

fun RecipeId.domId() = "recipe-$value"
