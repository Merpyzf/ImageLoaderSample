package lib.cache.memory

import kotlin.jvm.JvmOverloads
import kotlin.jvm.Synchronized
import java.lang.StringBuilder
import java.util.*

/*
 * A memory cache implementation which uses a LRU policy.
 * <p>
 * This implementation is thread safe.
 *
 * @author Shintaro Katafuchi
 */
class LruCache<K, V> @JvmOverloads constructor(capacity: Int = DEFAULT_CAPACITY) : Cache<K, V> {
    private val map: MutableMap<K, V>

    @get:Synchronized
    override val maxMemorySize: Int

    @get:Synchronized
    override var memorySize = 0
        private set

    companion object {
        /**
         * The flag represents remove all entries in the cache.
         */
        private const val REMOVE_ALL = -1
        private const val DEFAULT_CAPACITY = 10
    }

    init {
        require(capacity > 0) { "capacity <= 0" }
        map = LruHashMap(capacity)
        maxMemorySize = capacity
    }

    override fun get(key: K): V? {
        Objects.requireNonNull(key, "key == null")
        synchronized(this) {
            val value = map[key]
            if (value != null) {
                return value
            }
        }
        return null
    }

    override fun put(key: K, value: V): V? {
        Objects.requireNonNull(key, "key == null")
        Objects.requireNonNull(value, "value == null")
        var previous: V?
        synchronized(this) {
            previous = map.put(key, value)
            memorySize += getValueSize(value)
            if (previous != null) {
                memorySize -= getValueSize(previous)
            }
            trimToSize(maxMemorySize)
        }
        return previous
    }

    override fun remove(key: K): V? {
        Objects.requireNonNull(key, "key == null")
        var previous: V?
        synchronized(this) {
            previous = map.remove(key)
            if (previous != null) {
                memorySize -= getValueSize(previous)
            }
        }
        return previous
    }

    @Synchronized
    override fun clear() {
        trimToSize(REMOVE_ALL)
    }

    /**
     * Returns a copy of the current contents of the cache.
     */
    @Synchronized
    fun snapshot(): Map<K, V> {
        return LinkedHashMap(map)
    }

    /**
     * Returns the class name.
     *
     *
     * This method should be overridden to debug exactly.
     *
     * @return class name.
     */
    protected val className: String
        protected get() = LruCache::class.java.name

    /**
     * Returns the size of the entry.
     *
     *
     * The default implementation returns 1 so that max size is the maximum number of entries.
     *
     *
     * *Note:* This method should be overridden if you control memory size correctly.
     *
     * @param value value
     * @return the size of the entry.
     */
    protected fun getValueSize(value: V?): Int {
        return 1
    }

    /**
     * Remove the eldest entries.
     *
     *
     * *Note:* This method has to be called in synchronized block.
     *
     * @param maxSize max size
     */
    private fun trimToSize(maxSize: Int) {
        while (true) {
            if (memorySize <= maxSize || map.isEmpty()) {
                break
            }
            check(!(memorySize < 0 || map.isEmpty() && memorySize != 0)) { "$className.getValueSize() is reporting inconsistent results" }
            val toRemove: Map.Entry<K, V> = map.entries.iterator().next()
            map.remove(toRemove.key)
            memorySize -= getValueSize(toRemove.value)
        }
    }

    @Synchronized
    override fun toString(): String {
        val sb = StringBuilder()
        for ((key, value) in map) {
            sb.append(key)
                .append('=')
                .append(value)
                .append(",")
        }
        sb.append("maxMemory=")
            .append(maxMemorySize)
            .append(",")
            .append("memorySize=")
            .append(memorySize)
        return sb.toString()
    }

    override fun size(): Int {
        return map.size
    }
}