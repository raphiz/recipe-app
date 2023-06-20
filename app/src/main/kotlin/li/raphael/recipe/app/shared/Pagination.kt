package li.raphael.recipe.app.shared

import org.springframework.web.util.UriComponentsBuilder
import kotlin.math.ceil
import kotlin.math.max

data class Page(val offset: Int, val size: Int) {
    init {
        require(offset >= 0) { "Offset must not be negative $this" }
        require(size > 0) { "Size must be positive $this" }
    }

    val pageNumber: Int
        get() = (offset / size) + 1

    companion object {
        fun ofPageNumber(pageNumber: Int, size: Int) = Page((pageNumber - 1) * size, size)
        fun firstPage(size: Int): Page = Page(0, size)
        val singleResult = firstPage(1)
        val unbound = firstPage(Int.MAX_VALUE)
    }
}

fun Int.countPagesFor(total: Int) = maxOf(ceil(total.toDouble() / this.toDouble()).toInt(), 1)

data class PagedList<T>(val data: List<T>, val page: Page, val total: Int) : List<T> by data {

    val pageCount: Int
        get() = page.size.countPagesFor(total)

    fun firstPage() = Page(0, page.size)

    fun previousPage() = if (page.offset > 0) {
        Page(max(0, page.offset - page.size), page.size)
    } else {
        null
    }

    fun nextPage() = if (page.offset + page.size < total) {
        Page(page.offset + page.size, page.size)
    } else {
        null
    }

    fun lastPage() = Page(((total - 1) / page.size) * page.size, page.size)
}

fun String.withPagination(page: Page?): String = withQueryParameters(
    PAGE_NUMBER to page?.pageNumber?.toString(),
    PAGE_SIZE to page?.size?.toString(),
)
fun String.withQueryParameters(vararg queryParams: Pair<String, String?>): String =
    UriComponentsBuilder.fromUriString(this).apply {
        buildAndExpand()
        queryParams.forEach { (key, value) ->
            value?.let { queryParam(key, value) }
        }
    }.toUriString()
