package li.raphael.recipe.app

import com.fasterxml.jackson.databind.ObjectMapper
import li.raphael.recipe.app.recipe.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.net.URI

@Component
class ExampleDataGenerator(
    private val recipeRepository: RecipeRepository,
    private val resourceLoader: ResourceLoader,
    @Value("\${insert-example-data-on-startup:false}") private val insertExampleDataOnStartup: Boolean = false,
) {
    fun insertExampleData() {
        val mapper = ObjectMapper()
        val readTree = mapper.readTree(resourceLoader.getResource("classpath:/data/recipes.min.json").contentAsByteArray)
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
