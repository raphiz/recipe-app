package li.raphael.kotlinhtml.html

import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML
import java.io.OutputStream

typealias HtmlDsl = TagConsumer<*>.() -> Any?

fun HtmlDsl.renderToOutputStream(outputStream: OutputStream, prependDocType: Boolean = false) {
    outputStream.bufferedWriter().also { writer ->
        if (prependDocType) writer.append("<!DOCTYPE html>")
        this.invoke(writer.appendHTML())
    }.flush()
}

fun HtmlDsl.renderToString(): String {
    return StringBuilder().appendHTML().also { tagConsumer ->
        this.invoke(tagConsumer)
    }.finalize().toString()
}
