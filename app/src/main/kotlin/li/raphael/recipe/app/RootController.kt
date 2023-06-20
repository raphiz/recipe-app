package li.raphael.recipe.app

import jakarta.servlet.http.HttpServletRequest
import kotlinx.html.*
import li.raphael.kotlinhtml.errors.ErrorInformation
import li.raphael.kotlinhtml.html.HtmlView
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.recipe.app.components.renderPage
import li.raphael.recipe.app.shared.SSEService
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Controller
class RootController(private val sseService: SSEService) : ErrorController {

    @GetMapping(Routes.root)
    fun index() = Routes.redirect(Routes.recipes.list, HttpStatus.FOUND)

    @GetMapping("/events")
    @CrossOrigin
    fun eventSource(): SseEmitter = sseService.createEmitter()

    @RequestMapping(Routes.error)
    fun error(error: ErrorInformation, request: HttpServletRequest): HtmlView {
        return renderPage(
            title = t("error.title", error.status.reasonPhrase),
            httpStatus = error.status,
        ) {
            if (error.includeStackTrace && error.stackTrace != null) {
                pre {
                    code {
                        +error.stackTrace!!
                    }
                }
            }
        }
    }
}
