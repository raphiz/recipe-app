package li.raphael.recipe.app.recipe

import li.raphael.recipe.app.shared.Page
import li.raphael.recipe.app.shared.PagedList

interface RecipeRepository {
    fun list(searchText: String? = null, page: Page): PagedList<Recipe>
    fun save(recipe: Recipe)
    fun get(id: RecipeId): Recipe
    fun delete(id: RecipeId)
}
