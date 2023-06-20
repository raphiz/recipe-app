package li.raphael.recipe.app.recipe

import li.raphael.recipe.app.shared.Page
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URI

class InMemoryRecipeRepositoryTest {
    @Test
    fun `list returns an empty list when no recipes exist`() {
        val repository = recipeRepository()

        val recipes = repository.list(page = Page.unbound)

        assertThat(recipes).isEmpty()
    }

    @Test
    fun `list returns all recipes contained in the repository`() {
        val expectedRecipes = listOf(
            createRecipe(),
            createRecipe(),
        )
        val repository = recipeRepository(expectedRecipes)

        val actualRecipes = repository.list(page = Page.unbound)

        assertThat(actualRecipes).containsExactlyElementsOf(expectedRecipes)
    }

    @Test
    fun `list returns a recipe after it is inserted`() {
        val repository = recipeRepository()
        val newRecipe = createRecipe()

        repository.save(newRecipe)
        val actualRecipes = repository.list(page = Page.unbound)

        assertThat(actualRecipes).containsExactly(newRecipe)
    }

    @Test
    fun `list returns only the updated recipe after it is inserted`() {
        val originalRecipe = createRecipe()
        val repository = recipeRepository(originalRecipe)

        val updatedRecipe = originalRecipe.copy(title = "Chicken Tandoori")
        repository.save(updatedRecipe)
        val actualRecipes = repository.list(page = Page.unbound)

        assertThat(actualRecipes).containsExactly(updatedRecipe)
    }

    private fun createRecipe() = Recipe(
        RecipeId.random(),
        "Chicken Tikka Masala",
        null,
        URI("https://example.com/image.jpg"),
    )

    private fun recipeRepository(vararg recipes: Recipe) = recipeRepository(recipes.toList())
    private fun recipeRepository(recipes: List<Recipe>): RecipeRepository = InMemoryRecipeRepository(recipes)
}
