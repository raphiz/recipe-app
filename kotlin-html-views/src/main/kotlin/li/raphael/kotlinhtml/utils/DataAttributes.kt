package li.raphael.kotlinhtml.utils

import kotlinx.html.Tag

fun Tag.dataAttributes(vararg pairs: Pair<String, String>) {
    data = mutableMapOf(*pairs)
}

var Tag.data: MutableMap<String, String>
    get() {
        return DataAttributes(attributes)
    }
    set(value) {
        DataAttributes(attributes).putAll(value)
    }

class DataAttributes(
    private val attributes: MutableMap<String, String>,
) : java.util.AbstractMap<String, String>() {

    override val entries: MutableSet<MutableMap.MutableEntry<String, String>>
        get() = throw NotImplementedError()

    override val keys: MutableSet<String>
        get() = throw NotImplementedError()

    override val values: MutableCollection<String>
        get() = throw NotImplementedError()

    override val size: Int
        get() = dataAttributes().size

    override fun clear() = dataAttributes().forEach { attributes.remove(it.key) }

    override fun isEmpty(): Boolean = dataAttributes().isEmpty()

    override fun remove(key: String): String? = attributes.remove(mapKey(key))

    override fun putAll(from: Map<out String, String>) =
        attributes.putAll(from.mapKeys { mapKey(it.key) })

    override fun put(key: String, value: String): String? = attributes.put(mapKey(key), value)

    override fun get(key: String): String? = attributes[mapKey(key)]

    override fun containsValue(value: String): Boolean = dataAttributes().containsValue(value)

    override fun containsKey(key: String): Boolean = attributes.containsKey(mapKey(key))

    private fun dataAttributes() = attributes.filter { it.key.startsWith("data-") }

    private fun mapKey(key: String) = "data-$key"
}
