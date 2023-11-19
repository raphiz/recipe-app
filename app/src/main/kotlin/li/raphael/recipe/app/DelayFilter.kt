package li.raphael.recipe.app

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

class DelayFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        TimeUnit.MILLISECONDS.sleep(200)
        chain.doFilter(request, response)
    }
}

@Configuration
class FilterConfig {
    @Bean
    fun delayFilter(): FilterRegistrationBean<DelayFilter> {
        val registrationBean = FilterRegistrationBean<DelayFilter>()
        registrationBean.setFilter(DelayFilter())
        registrationBean.addUrlPatterns("/*")
        return registrationBean
    }
}
