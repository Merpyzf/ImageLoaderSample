package lib.cache.memory

/**
 * A memory cache interface.
 *
 * @author Shintaro Katafuchi
 */
interface Cache<K, V> {
    /**
     * Gets an value for the specified `key` or return `null`.
     *
     * @param key key
     * @return the value or `null`.
     */
    operator fun get(key: K): V?

    /**
     * Puts an value in the cache for the specified `key`.
     *
     * @param key   key
     * @param value image
     * @return the previous value.
     */
    fun put(key: K, value: V): V?

    /**
     * Removes the entry for `key` if it exists or return `null`.
     *
     * @return the previous value or @{code null}.
     */
    fun remove(key: K): V?

    /**
     * Clears all the entries in the cache.
     */
    fun clear()

    /**
     * Get Current Cache entry size
     */
    fun size(): Int

    /**
     * Returns the max memory size of the cache.
     *
     * @return max memory size.
     */
    val maxMemorySize: Int

    /**
     * Returns the current memory size of the cache.
     *
     * @return current memory size.
     */
    val memorySize: Int
}