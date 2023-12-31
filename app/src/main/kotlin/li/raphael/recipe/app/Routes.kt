package li.raphael.recipe.app

import li.raphael.recipe.app.recipe.RecipeId
import li.raphael.recipe.app.shared.Page
import li.raphael.recipe.app.shared.withPagination
import li.raphael.recipe.app.shared.withQueryParameters
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
    const val edit = "/recipes/{id}/edit"
    const val delete = "/recipes/{id}/delete"

    fun list(searchText: String?, page: Page?): String = list
        .withPagination(page)
        .withQueryParameters(Params.searchFieldName to searchText)

    fun delete(id: RecipeId): String = "/recipes/${id.value}/delete"

    fun edit(id: RecipeId): String = "/recipes/${id.value}/edit"

    object Params {
        const val searchFieldName = "search"
    }
}
