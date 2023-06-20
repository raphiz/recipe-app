package li.raphael.recipe.app

import li.raphael.recipe.app.shared.PageHandlerMethodArgumentResolver
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class RecipeApp : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(PageHandlerMethodArgumentResolver())
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(RecipeApp::class.java, *args)
}
