package li.raphael.kotlinhtml.stimulus

import li.raphael.kotlinhtml.ImportMap
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StimulusConfiguration {
    @Bean
    fun stimulusControllerRegistry(
        importMap: ImportMap,
        webProperties: WebProperties,
    ) = StimulusControllerRegistry(importMap, webProperties)
}
