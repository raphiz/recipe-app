package li.raphael.recipe.app.recipe

import java.net.URI
import java.util.UUID

data class Recipe(
    val id: RecipeId,
    val title: String,
    val source: URI?,
    val image: URI,
)

@JvmInline
value class RecipeId(val value: UUID) {
    companion object {
        fun random(): RecipeId = RecipeId(UUID.randomUUID())
    }
}
