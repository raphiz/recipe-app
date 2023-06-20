package li.raphael.recipe.app.shared

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

const val PAGE_SIZE = "pageSize"
const val PAGE_NUMBER = "pageNumber"
const val PAGE_OFFSET = "pageOffset"
const val DEFAULT_PAGE_SIZE = 8

class PageHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.parameterType == Page::class.java
    }

    override fun resolveArgument(
        methodParameter: MethodParameter,
        modelAndViewContainer: ModelAndViewContainer?,
        nativeWebRequest: NativeWebRequest,
        webDataBinderFactory: WebDataBinderFactory?,
    ): Page {
        val size = nativeWebRequest.getParameter(PAGE_SIZE)?.toIntOrNull() ?: DEFAULT_PAGE_SIZE
        val number = nativeWebRequest.getParameter(PAGE_NUMBER)?.toIntOrNull()
        val offset = nativeWebRequest.getParameter(PAGE_OFFSET)?.toIntOrNull()

        return if (offset != null) {
            Page(offset, size = size)
        } else if (number != null) {
            Page.ofPageNumber(number, size)
        } else {
            Page.firstPage(size)
        }
    }
}
