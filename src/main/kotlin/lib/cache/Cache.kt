package lib.cache

interface Cache<K, V> {
    fun store(key: K, value: V)
    fun contains(key: K): Boolean
    fun get(key: K): V?
    fun memoryCacheSize(): Int
    fun fileCacheSize(): Int
    fun evictOldest(): ByteArray?
    fun clear()
}