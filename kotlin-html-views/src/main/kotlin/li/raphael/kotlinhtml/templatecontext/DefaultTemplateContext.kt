package li.raphael.kotlinhtml.templatecontext

import jakarta.servlet.http.HttpServletRequest
import kotlinx.html.MetaDataContent
import kotlinx.html.link
import kotlinx.html.script
import kotlinx.html.unsafe
import li.raphael.kotlinhtml.ImportMap
import li.raphael.kotlinhtml.devtools.DevToolsEnablementService
import li.raphael.kotlinhtml.stimulus.StimulusControllerRegistry
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class DefaultTemplateContext(
    private val stimulusControllerRegistry: StimulusControllerRegistry,
    private val importMap: ImportMap,
    private val devToolsEnablementService: DevToolsEnablementService,
    private val messageSource: MessageSource,
) {
    fun translate(code: String, vararg args: Any): String {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
    }

    val locale
        get() = LocaleContextHolder.getLocale()

    fun MetaDataContent.liveReload() {
        if (devToolsEnablementService.isEnabled()) {
            script(src = "http://localhost:35729/livereload.js") {
                defer = true
            }
        }
    }

    fun MetaDataContent.importmap() {
        script(type = "importmap") {
            unsafe {
                +importMap.jsonValue
            }
        }
    }

    fun MetaDataContent.stimulusModules() {
        val controllers = stimulusControllerRegistry.stimulusControllers
        controllers.forEach {
            link(rel = "modulepreload", href = it.url)
        }
        script(type = "module") {
            unsafe {
                +stimulusControllerRegistry.autoLoadControllersJs()
            }
        }
    }

    val request: HttpServletRequest
        get() {
            val requestAttributes = RequestContextHolder.getRequestAttributes()
            check(requestAttributes is ServletRequestAttributes) { "Could not retrieve active request." }
            return requestAttributes.request
        }
}
