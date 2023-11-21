package li.raphael.kotlinhtml.tags

import kotlinx.html.*
import li.raphael.kotlinhtml.utils.filterValuesNotNull

class Template(
    consumer: TagConsumer<*>,
    id: String?,
) : HTMLTag("template", consumer, mapOf("id" to id).filterValuesNotNull(), inlineTag = false, emptyTag = false),
    HtmlBlockTag

@HtmlTagMarker
inline fun FlowContent.template(id: String? = null, crossinline block: Template.() -> Unit) =
    Template(consumer, id).visit(block)

@HtmlTagMarker
inline fun MetaDataContent.template(id: String? = null, crossinline block: Template.() -> Unit) =
    Template(consumer, id).visit(block)
