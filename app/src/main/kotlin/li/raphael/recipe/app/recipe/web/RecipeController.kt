package li.raphael.recipe.app.recipe.web

import kotlinx.html.*
import li.raphael.recipe.app.RecipeRoutes
import li.raphael.recipe.app.Routes
import li.raphael.recipe.app.recipe.Recipe
import li.raphael.recipe.app.recipe.RecipeId
import li.raphael.recipe.app.recipe.RecipeRepository
import li.raphael.recipe.app.recipe.web.views.*
import li.raphael.recipe.app.shared.Page
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.View
import java.net.URI
import java.util.*

@Controller
class RecipeController(
    private val recipeRepository: RecipeRepository,
) {

    @GetMapping(RecipeRoutes.list)
    fun list(
        @RequestParam(RecipeRoutes.Params.searchFieldName) searchText: String?,
        page: Page,
    ): View {
        val recipes = recipeRepository.list(searchText, page)
        return renderRecipeList(recipes)
    }

    @PostMapping(RecipeRoutes.delete)
    fun delete(
        @PathVariable id: String,
        page: Page,
    ): View {
        val recipeId = RecipeId(UUID.fromString(id))
        recipeRepository.delete(recipeId)
        return Routes.redirect(Routes.recipes.list(page), HttpStatus.SEE_OTHER)
    }

    @GetMapping(RecipeRoutes.create)
    fun create(): View {
        return renderRecipeForm()
    }

    @PostMapping(RecipeRoutes.create)
    fun postCreate(@RequestParam("title") recipeTitle: String): View {
        Thread.sleep(2_000)
        recipeRepository.save(Recipe(RecipeId.random(), recipeTitle, null, URI("https://img.freepik.com/free-vector/realistic-white-plate-isolated_1284-41743.jpg")))
        return Routes.redirect(Routes.recipes.create, HttpStatus.SEE_OTHER)
    }
}
