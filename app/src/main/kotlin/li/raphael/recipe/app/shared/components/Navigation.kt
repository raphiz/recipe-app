package li.raphael.recipe.app.shared.components

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.data
import li.raphael.recipe.app.Routes

data class UNavigationLink(val href: String, val label: String)
fun BODY.uNavigation(vararg links: UNavigationLink, isActive: (UNavigationLink) -> Boolean) {
    header("usa-header usa-header--basic") {
        div("usa-nav-container") {
            data["controller"] = "reveal"
            data["reveal-reveal-class"] = "is-visible"
            div("usa-navbar") {
                logo(t("app.title"), Routes.root)
                button(classes = "usa-menu-btn") {
                    type = ButtonType.button
                    data["action"] = "click->reveal#show"
                    +t("menu.title")
                }
            }
            nav("usa-nav") {
                data["reveal-target"] = "item"
                button(classes = "usa-nav__close") {
                    attributes["aria-label"] = t("menu.close")
                    data["action"] = "click->reveal#hide"
                    uIcon(IconType.CLOSE)
                }
                ul("usa-nav__primary usa-accordion") {
                    links.forEach {
                        uNavigationLink(href = it.href, label = it.label, isActive = isActive(it))
                    }
                }
            }
        }
    }
}

private fun UL.uNavigationLink(href: String, label: String, isActive: Boolean) {
    li("usa-nav__primary-item") {
        a(classes = "usa-nav-link") {
            if (isActive) {
                attributes["aria-current"] = "page"
                classes += "usa-current"
            }
            this.href = href
            span { +label }
        }
    }
}

private fun DIV.logo(appTitle: String, rootUrl: String) {
    div("usa-logo") {
        em("usa-logo__text") {
            a(href = rootUrl) {
                title = appTitle
                +appTitle
            }
        }
    }
}
