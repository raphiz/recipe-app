package li.raphael.kotlinhtml.errors

import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ErrorConfiguration(
    val applicationContext: GenericApplicationContext,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        val errorInformation = applicationContext.getBean(ErrorInformation::class.java)
        resolvers.add(ErrorInformationResolver(errorInformation))
    }

    @Bean
    fun errorInformation(errorAttributes: ErrorAttributes, serverProperties: ServerProperties) =
        ErrorInformation(errorAttributes, serverProperties)
}
