package li.raphael.kotlinhtml.utils

fun <K, V> Map<K, V?>.filterValuesNotNull() =
    mapNotNull { (k, v) -> v?.let { k to v } }.toMap()
