package li.raphael.recipe.app.components

import kotlinx.html.*
import li.raphael.kotlinhtml.html.HtmlView
import li.raphael.kotlinhtml.templatecontext.*
import li.raphael.recipe.app.Routes
import li.raphael.recipe.app.shared.components.*
import org.springframework.http.HttpStatus

private const val mainContainerId = "main"

fun renderPage(
    title: String,
    httpStatus: HttpStatus? = null,
    content: FlowContent.() -> Unit,
): HtmlView {
    return HtmlView(httpStatus ?: HttpStatus.OK) {
        html {
            lang = locale.toLanguageTag()
            head {
                meta(charset = "utf-8")
                meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                link(rel = "stylesheet", href = "/uswds.css")
                link(rel = "stylesheet", href = "/application.css")
                title(title)

                script(src = "/es-module-shims.js") { async = true }
                importmap()
                link(rel = "modulepreload", href = "/application.mjs")
                script(type = "module") { unsafe { +"""import "/application.mjs"""" } }
                stimulusControllerModules()
                liveReload()
            }
            body {
                uSkiplink(mainContainerId, t("skiplink"))
                uNavigation(
                    UNavigationLink(Routes.recipes.list, t("navigation.recipes")),
                    UNavigationLink(Routes.recipes.create, t("navigation.new-recipe")),
                    isActive = { request().requestURI == it.href },
                )
                uMain(id = mainContainerId, title = title) {
                    content()
                }
                confirmDialogTemplate()
            }
        }
    }
}
