package li.raphael.recipe.app

import com.fasterxml.jackson.databind.ObjectMapper
import li.raphael.recipe.app.recipe.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.net.URI

@Component
class ExampleDataGenerator(
    private val recipeRepository: RecipeRepository,
    @Value("\${insert-example-data-on-startup:false}") private val insertExampleDataOnStartup: Boolean = false,
) {
    fun insertExampleData() {
        val mapper = ObjectMapper()
        val readTree = mapper.readTree(ResourceUtils.getFile("classpath:data/recipes.min.json"))
        return readTree.asSequence().map {
            Recipe(
                id = RecipeId.random(),
                title = it["name"].textValue(),
                source = URI(it["source"].textValue()),
                image = URI(it["image"].textValue()),
            )
        }.forEach(recipeRepository::save)
    }

    @EventListener
    fun onApplicationReady(e: ApplicationReadyEvent) {
        if (insertExampleDataOnStartup) {
            insertExampleData()
        }
    }
}
