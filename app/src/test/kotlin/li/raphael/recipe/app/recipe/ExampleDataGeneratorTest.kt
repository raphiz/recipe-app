package li.raphael.recipe.app.recipe

import li.raphael.recipe.app.ExampleDataGenerator
import li.raphael.recipe.app.shared.Page
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ExampleDataGeneratorTest {

    @Test
    fun `insertExampleData inserts multiple entries into the repository`() {
        val recipeRepository = InMemoryRecipeRepository(mutableListOf())
        val generator = ExampleDataGenerator(recipeRepository)

        generator.insertExampleData()

        val allEntries = recipeRepository.list(page = Page.unbound)
        assertThat(allEntries)
            .isNotEmpty
            .hasSizeGreaterThan(50)
    }
}
