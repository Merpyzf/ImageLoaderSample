package lib.cache.memory

import java.util.LinkedHashMap

/**
 * Custom [java.util.HashMap] using a LRU policy.
 *
 * @param <K> key
 * @param <V> value
 * @author Shintaro Katafuchi
</V></K> */
internal class LruHashMap<K, V>(private val capacity: Int) : LinkedHashMap<K, V>(
    capacity, 0.75f, true
) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        return size > capacity
    }
}