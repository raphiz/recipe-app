package li.raphael.recipe.app.recipe

import li.raphael.recipe.app.shared.Page
import li.raphael.recipe.app.shared.PagedList
import org.springframework.stereotype.Repository
import java.util.concurrent.CopyOnWriteArrayList

@Repository
class InMemoryRecipeRepository(recipes: List<Recipe>) : RecipeRepository {
    private val recipes = CopyOnWriteArrayList(recipes)

    override fun list(searchText: String?, page: Page): PagedList<Recipe> {
        val allResults = recipes
            .filter { it.title.contains(searchText ?: "", ignoreCase = true) }

        val filtered = allResults.asSequence().drop(page.offset).take(page.size).toList()
        return PagedList(
            filtered,
            page,
            total = allResults.size,
        )
    }

    override fun save(recipe: Recipe) {
        this.recipes.removeIf { it.id == recipe.id }
        this.recipes.add(recipe)
    }

    override fun delete(id: RecipeId) {
        this.recipes.removeIf { it.id == id }
    }
}
