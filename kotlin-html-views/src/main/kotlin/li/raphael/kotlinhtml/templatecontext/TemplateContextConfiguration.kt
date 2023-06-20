package li.raphael.kotlinhtml.templatecontext

import li.raphael.kotlinhtml.ImportMap
import li.raphael.kotlinhtml.devtools.DevToolsEnablementService
import li.raphael.kotlinhtml.stimulus.StimulusControllerRegistry
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TemplateContextConfiguration {
    @Bean
    fun defaultTemplateContext(
        stimulusControllerRegistry: StimulusControllerRegistry,
        importMap: ImportMap,
        devToolsEnablementService: DevToolsEnablementService,
        messageSource: MessageSource,

    ) = DefaultTemplateContext(stimulusControllerRegistry, importMap, devToolsEnablementService, messageSource)
}
