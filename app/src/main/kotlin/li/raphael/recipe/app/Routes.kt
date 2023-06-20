package li.raphael.recipe.app

import li.raphael.recipe.app.recipe.RecipeId
import li.raphael.recipe.app.shared.Page
import li.raphael.recipe.app.shared.withPagination
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.View
import org.springframework.web.servlet.view.RedirectView

object Routes {
    val recipes = RecipeRoutes

    const val root: String = "/"
    const val error: String = "/error"

    fun redirect(url: String, statusCode: HttpStatus): View =
        RedirectView(url).apply { setStatusCode(statusCode) }
}

object RecipeRoutes {
    const val list = "/recipes/"
    const val create = "/recipes/new"
    const val delete = "/recipes/{id}/delete"

    fun list(page: Page?): String = list
        .withPagination(page)

    fun delete(id: RecipeId): String = "/recipes/${id.value}/delete"

    object Params {
        const val searchFieldName = "search"
    }
}
