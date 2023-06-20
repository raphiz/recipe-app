package li.raphael.kotlinhtml.tags

import kotlinx.html.*

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

private fun <K, V> Map<K, V?>.filterValuesNotNull() =
    mapNotNull { (k, v) -> v?.let { k to v } }.toMap()
