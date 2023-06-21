package li.raphael.recipe.app.shared.components

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.kotlinhtml.utils.dataAttributes

fun FlowContent.uSearchForm(fieldName: String, searchText: String?, customizer: FORM.() -> Unit = {}) {
    form(classes = "usa-search margin-bottom-4") {
        customizer()
        dataAttributes(
            "action" to "keyup->search-form#debouncedSubmit search->search-form#debouncedSubmit",
            "controller" to "search-form",
        )
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
    }
}
