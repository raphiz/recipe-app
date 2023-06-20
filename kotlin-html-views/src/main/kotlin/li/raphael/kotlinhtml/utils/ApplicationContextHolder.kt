package li.raphael.kotlinhtml.utils

import org.springframework.context.ApplicationContext

object ApplicationContextHolder {
    private lateinit var applicationContext: ApplicationContext
    inline fun <reified T : Any> getBean() = getBean(T::class.java)

    fun <T> getBean(cls: Class<T>): T = applicationContext.getBean(cls)

    fun setApplicationContext(applicationContext: ApplicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext
    }
}
