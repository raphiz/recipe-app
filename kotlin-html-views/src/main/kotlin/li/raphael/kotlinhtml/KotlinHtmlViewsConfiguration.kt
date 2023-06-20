package li.raphael.kotlinhtml

import com.fasterxml.jackson.databind.ObjectMapper
import li.raphael.kotlinhtml.devtools.DeveloperToolConfiguration
import li.raphael.kotlinhtml.errors.ErrorConfiguration
import li.raphael.kotlinhtml.stimulus.StimulusConfiguration
import li.raphael.kotlinhtml.templatecontext.TemplateContextConfiguration
import li.raphael.kotlinhtml.utils.ApplicationContextHolder
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWarDeployment
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.web.servlet.resource.ResourceUrlProvider

@AutoConfiguration
@ConditionalOnNotWarDeployment
@ConditionalOnWebApplication
@Import(DeveloperToolConfiguration::class, ErrorConfiguration::class, StimulusConfiguration::class, TemplateContextConfiguration::class)
class KotlinHtmlViewsConfiguration : WebServerFactoryCustomizer<AbstractServletWebServerFactory> {
    override fun customize(factory: AbstractServletWebServerFactory) {
        // .mjs for JS modules, see https://v8.dev/features/modules#mjs
        factory.mimeMappings.add("mjs", "text/javascript; charset=utf-8")
        // .map for source maps
        factory.mimeMappings.add("map", "application/json; charset=utf-8")
    }

    @Bean
    fun importMap(resourceUrlProvider: ResourceUrlProvider, objectMapper: ObjectMapper) =
        ImportMap(resourceUrlProvider, objectMapper)

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent) {
        ApplicationContextHolder.setApplicationContext(event.applicationContext)
    }
}
