package li.raphael.kotlinhtml.html

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.View

const val TEXT_HTML_CONTENT_TYPE = "text/html; charset=utf-8"
const val TEXT_VND_TURBO_STREAM = "text/vnd.turbo-stream.html; charset=utf-8"

class HtmlView(
    private val httpStatus: HttpStatus,
    private val contentType: String = TEXT_HTML_CONTENT_TYPE,
    private val htmlDsl: HtmlDsl,
) : View {
    override fun getContentType() = TEXT_HTML_CONTENT_TYPE
    override fun render(
        model: MutableMap<String, *>?,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        response.status = httpStatus.value()
        response.contentType = contentType
        htmlDsl.renderToOutputStream(response.outputStream, prependDocType = contentType == TEXT_HTML_CONTENT_TYPE)
    }
}
