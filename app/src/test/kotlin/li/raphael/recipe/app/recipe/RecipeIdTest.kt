package li.raphael.recipe.app.recipe

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RecipeIdTest {
    @Test
    fun `random returns a new recipe id on each call`() {
        val numberOfIds = 10
        val generatedIds = generateSequence { RecipeId.random() }.take(numberOfIds).toList()
        assertThat(generatedIds.toSet()).hasSize(numberOfIds)
    }
}
