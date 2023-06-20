package li.raphael.recipe.app.shared

import li.raphael.kotlinhtml.html.HtmlDsl
import li.raphael.kotlinhtml.html.renderToString
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.concurrent.CopyOnWriteArrayList

@Service
class SSEService {
    private val emitters: MutableList<SseEmitter> = CopyOnWriteArrayList()

    fun broadcast(htmlDsl: HtmlDsl) {
        emitters.forEach {
            it.sendEvent(htmlDsl)
        }
    }

    private fun SseEmitter.sendEvent(htmlDsl: HtmlDsl) = try {
        send(htmlDsl.renderToString().replace("\n", ""))
    } catch (e: Exception) {
        logger.debug("Ignoring exception handled by the emitter onError callback", e)
    }

    fun createEmitter(): SseEmitter {
        return SseEmitter().apply {
            onCompletion {
                logger.info("Emitter completed: $this")
                if (emitters.contains(this)) emitters.remove(this)
            }
            onTimeout {
                logger.info("Emitter timed out: $this")
                if (emitters.contains(this)) emitters.remove(this)
            }
            onError {
                if (it is IOException) {
                    // This happens for example when a user closes the browser tab.
                    logger.debug("Got IO Error on SSE Emitter", it)
                } else {
                    logger.error("Error occurred while processing SSE Emitter $this", it)
                }
            }
            emitters.add(this)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.declaringClass)
    }
}
