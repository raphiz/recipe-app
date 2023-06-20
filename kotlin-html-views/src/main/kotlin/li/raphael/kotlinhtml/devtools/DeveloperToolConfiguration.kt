package li.raphael.kotlinhtml.devtools

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.devtools.system.DevToolsEnablementDeducer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DeveloperToolConfiguration {
    @Bean
    @ConditionalOnClass(DevToolsEnablementDeducer::class)
    fun devToolsOnClassPathDevToolsEnablementService(): DevToolsEnablementService {
        return DevToolsEnablementService { DevToolsEnablementDeducer.shouldEnable(Thread.currentThread()) }
    }

    @Bean
    @ConditionalOnMissingBean
    fun devToolsDisabledEnablementService(): DevToolsEnablementService {
        return DevToolsEnablementService { false }
    }
}
