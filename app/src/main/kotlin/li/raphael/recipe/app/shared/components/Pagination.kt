package li.raphael.recipe.app.shared.components

import kotlinx.html.*
import li.raphael.kotlinhtml.templatecontext.t
import li.raphael.recipe.app.shared.Page
import li.raphael.recipe.app.shared.PagedList

fun <T> FlowContent.uPagination(
    pagedList: PagedList<T>,
    pageUrl: (Page) -> String,
) {
    nav("usa-pagination") {
        attributes["aria-label"] = "Pagination"
        ol("usa-pagination__list") {
            when (val previousPage = pagedList.previousPage()) {
                null -> {}
                else -> {
                    li("usa-pagination__item usa-pagination__arrow") {
                        a(classes = "usa-pagination__link usa-pagination__previous-page") {
                            href = pageUrl(previousPage)
                            uIcon(IconType.NAVIGATE_BEFORE)
                            span("usa-pagination__link-text") { +t("paging.previous-page") }
                        }
                    }
                }
            }
            pagesToDisplay(pagedList).forEach { currentPage ->
                if (currentPage == null) {
                    li("usa-pagination__item usa-pagination__overflow") { +"…" }
                } else {
                    li("usa-pagination__item usa-pagination__page-no") {
                        a(classes = "usa-pagination__button") {
                            if (currentPage == pagedList.page) {
                                classes += "usa-current"
                                attributes["aria-current"] = "page"
                            }
                            href = pageUrl(currentPage)
                            title = t("paging.page", currentPage.pageNumber)
                            +"${currentPage.pageNumber}"
                        }
                    }
                }
            }
            when (val nextPage = pagedList.nextPage()) {
                null -> {}
                else -> {
                    li("usa-pagination__item usa-pagination__arrow") {
                        a(classes = "usa-pagination__link usa-pagination__next-page") {
                            href = pageUrl(nextPage)

                            span("usa-pagination__link-text") { +t("paging.next-page") }
                            uIcon(IconType.NAVIGATE_NEXT)
                        }
                    }
                }
            }
        }
    }
}

private const val INCLUDE_FIRST_AND_LAST_N_PAGES = 2
private const val INCLUDE_N_PAGES_BEFORE_AND_AFTER_CURRENT_PAGE = 2

fun pagesToDisplay(pagedList: PagedList<*>): List<Page?> {
    val pageCount = pagedList.pageCount
    val currentPage = pagedList.page.pageNumber

    val maxNumberOfPages =
        1 /* current element */ + 2 * (INCLUDE_FIRST_AND_LAST_N_PAGES + 1 /* null */ + INCLUDE_N_PAGES_BEFORE_AND_AFTER_CURRENT_PAGE)
    val end = maxNumberOfPages - 1 - INCLUDE_FIRST_AND_LAST_N_PAGES

    fun fromStart(n: Int) = 1..n
    fun fromEnd(n: Int) = pageCount - n + 1..pageCount

    return when {
        pageCount <= maxNumberOfPages -> 1..pageCount

        currentPage in fromStart(end - INCLUDE_N_PAGES_BEFORE_AND_AFTER_CURRENT_PAGE) -> combineRangesWithGap(
            fromStart(end),
            fromEnd(INCLUDE_FIRST_AND_LAST_N_PAGES),
        )

        currentPage in fromEnd(end - INCLUDE_N_PAGES_BEFORE_AND_AFTER_CURRENT_PAGE) -> combineRangesWithGap(
            fromStart(INCLUDE_FIRST_AND_LAST_N_PAGES),
            fromEnd(end),
        )

        else -> combineRangesWithGap(
            fromStart(INCLUDE_FIRST_AND_LAST_N_PAGES),
            currentPage - INCLUDE_N_PAGES_BEFORE_AND_AFTER_CURRENT_PAGE..currentPage + INCLUDE_N_PAGES_BEFORE_AND_AFTER_CURRENT_PAGE,
            fromEnd(INCLUDE_FIRST_AND_LAST_N_PAGES),
        )
    }.map { it?.let { Page.ofPageNumber(it, maxOf(pagedList.page.size, 1)) } }
}

fun combineRangesWithGap(vararg ranges: IntRange): List<Int?> {
    return ranges.flatMap { it.toList() + null }.dropLast(1)
}
