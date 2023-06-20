package li.raphael.kotlinhtml.templatecontext

import kotlinx.html.MetaDataContent
import kotlinx.html.Tag
import li.raphael.kotlinhtml.utils.ApplicationContextHolder.getBean
import org.jetbrains.annotations.PropertyKey

const val MESSAGES = "messages"

fun MetaDataContent.liveReload() =
    getBean<DefaultTemplateContext>().apply { liveReload() }

fun MetaDataContent.importmap() =
    getBean<DefaultTemplateContext>().apply { importmap() }

fun MetaDataContent.stimulusControllerModules() =
    getBean<DefaultTemplateContext>().apply { stimulusModules() }

fun Tag.request() =
    getBean<DefaultTemplateContext>().request

fun t(
    @PropertyKey(resourceBundle = MESSAGES) code: String,
    vararg args: Any,
): String = getBean<DefaultTemplateContext>()
    .translate(code, *args)

val locale
    get() = getBean<DefaultTemplateContext>().locale
