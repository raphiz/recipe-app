package li.raphael.kotlinhtml.errors

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.autoconfigure.web.ErrorProperties
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.context.request.ServletWebRequest

class ErrorInformation(
    private val errorAttributes: ErrorAttributes,
    private val serverProperties: ServerProperties,
) {
    val includeStackTrace: Boolean
        get() {
            return when (serverProperties.error.includeStacktrace) {
                ErrorProperties.IncludeAttribute.ALWAYS -> true
                ErrorProperties.IncludeAttribute.ON_PARAM -> request.getParameter("trace").toBoolean()
                else -> false
            }
        }

    val stackTrace: String?
        get() {
            val errorAttributes = errorAttributes.getErrorAttributes(
                ServletWebRequest(request),
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE),
            )
            return errorAttributes["trace"] as String?
        }
    val status: HttpStatus
        get() {
            val statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int?
                ?: return HttpStatus.INTERNAL_SERVER_ERROR
            return HttpStatus.resolve(statusCode) ?: HttpStatus.INTERNAL_SERVER_ERROR
        }
    private val request: HttpServletRequest
        get() = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
}
