package li.raphael.recipe.app.recipe.web

import li.raphael.kotlinhtml.html.HtmlView
import li.raphael.kotlinhtml.html.TEXT_VND_TURBO_STREAM
import li.raphael.kotlinhtml.tags.StreamAction
import li.raphael.kotlinhtml.tags.turboFrame
import li.raphael.kotlinhtml.tags.turboStream
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.recipe.app.RecipeRoutes
import li.raphael.recipe.app.Routes
import li.raphael.recipe.app.recipe.Recipe
import li.raphael.recipe.app.recipe.RecipeId
import li.raphael.recipe.app.recipe.RecipeRepository
import li.raphael.recipe.app.recipe.web.views.*
import li.raphael.recipe.app.shared.Page
import li.raphael.recipe.app.shared.SSEService
import li.raphael.recipe.app.shared.components.NOTIFICATION_AREA
import li.raphael.recipe.app.shared.components.cardGroupItem
import li.raphael.recipe.app.shared.components.uNotification
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
    private val sseService: SSEService,
) {

    @GetMapping(RecipeRoutes.list)
    fun list(
        @RequestParam(RecipeRoutes.Params.searchFieldName) searchText: String?,
        page: Page,
    ): View {
        val recipes = recipeRepository.list(searchText, page)
        return renderRecipeList(recipes, searchText)
    }

    @PostMapping(RecipeRoutes.delete)
    fun delete(
        @PathVariable id: String,
        page: Page,
        @RequestParam(RecipeRoutes.Params.searchFieldName) searchText: String?,
    ): View {
        val recipeId = RecipeId(UUID.fromString(id))
        recipeRepository.delete(recipeId)

        sseService.broadcast {
            turboStream(StreamAction.REMOVE, recipeId.domId())
        }

        return Routes.redirect(Routes.recipes.list(searchText, page), HttpStatus.SEE_OTHER)
    }

    @PostMapping(RecipeRoutes.edit)
    fun postEdit(
        @PathVariable id: String,
        @RequestParam("title") recipeTitle: String,
    ): View {
        val recipeId = RecipeId(UUID.fromString(id))
        val recipe = recipeRepository.get(recipeId).copy(title = recipeTitle)
        recipeRepository.save(recipe)

        sseService.broadcast {
            turboStream(StreamAction.REPLACE, recipe.id.domId()) {
                cardGroupItem(recipe.id.domId()) {
                    recipeCard(recipe)
                }
            }
        }

        return HtmlView(HttpStatus.OK, TEXT_VND_TURBO_STREAM) {
            turboStream(StreamAction.REPLACE, recipe.id.domId()) {
                cardGroupItem(recipe.id.domId()) {
                    recipeCard(recipe)
                }
            }
        }
    }

    @GetMapping(RecipeRoutes.edit)
    fun edit(
        @PathVariable id: String,
        page: Page,
        @RequestParam(RecipeRoutes.Params.searchFieldName) searchText: String?,
    ): View {
        val recipeId = RecipeId(UUID.fromString(id))
        val recipe = recipeRepository.get(recipeId)

        return HtmlView(HttpStatus.OK) {
            turboFrame(recipe.id.domId()) {
                editRecipeCard(recipe, page, searchText)
            }
        }
    }

    @GetMapping(RecipeRoutes.create)
    fun create(): View {
        return renderRecipeForm()
    }

    @PostMapping(RecipeRoutes.create)
    fun postCreate(
        @RequestParam("title") recipeTitle: String,
    ): View {
        Thread.sleep(2_000)
        recipeRepository.save(
            Recipe(
                RecipeId.random(),
                recipeTitle,
                null,
                URI("https://img.freepik.com/free-vector/realistic-white-plate-isolated_1284-41743.jpg"),
            ),
        )
        return HtmlView(HttpStatus.OK, TEXT_VND_TURBO_STREAM) {
            turboStream(StreamAction.APPEND, NOTIFICATION_AREA) {
                uNotification(t("recipes.create.created", recipeTitle))
            }
        }
    }
}
