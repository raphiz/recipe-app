package li.raphael.recipe.app.shared.components

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t

fun FlowContent.uSearchForm(fieldName: String, searchText: String?, customizer: FORM.() -> Unit = {}) {
    form(classes = "usa-search margin-bottom-4") {
        customizer()
        role = "search"
        label {
            htmlFor = fieldName
            uScreenReaderOnly { +t("search.field.label") }
        }
        input(type = InputType.search, name = fieldName, classes = "usa-input") {
            this.id = fieldName
            searchText?.let { value = searchText }
            placeholder = t("search.field.label")
        }
        uButton {
            type = ButtonType.submit
            span("usa-search__submit-text") { +t("search.button.label") }
            img(classes = "usa-search__submit-icon") {
                src = "/img/usa-icons-bg/search--white.svg"
                alt = t("search.button.label")
            }
        }
    }
}
