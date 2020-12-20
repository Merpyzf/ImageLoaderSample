package lib.cache

import lib.cache.file.FileCache
import lib.cache.memory.LruCache
import lib.helper.toHexStr

class DefaultCache : Cache<String, ByteArray> {
    var lruCache: LruCache<String, ByteArray> = LruCache((Runtime.getRuntime().totalMemory() / 5).toInt())

    @Synchronized
    override fun store(key: String, value: ByteArray) {
        if (value.isNotEmpty()) {
            println("缓存到内存中${key}")
            // store to memory
            if (lruCache[key.toHexStr()] == null) {
                lruCache.put(key.toHexStr(), value)
                println("内存中缓存的数量：${lruCache.size()}")
            }
            // store to disk
            if (!FileCache.exists(key.toHexStr())) {
                FileCache.put(key.toHexStr(), value)
            }
        }
    }

    @Synchronized
    override fun contains(key: String): Boolean {
        return if (lruCache[key.toHexStr()] != null) {
            true
        } else FileCache.exists(key.toHexStr())
    }

    @Synchronized
    override fun get(key: String): ByteArray? {
        var content: ByteArray? = lruCache[key.toHexStr()]
        if (content != null) {
            return content
        }
        content = FileCache.get(key.toHexStr())
        if (content != null) {
            return content
        }
        return null
    }

    override fun fileCacheSize(): Int {
        return FileCache.size()
    }

    override fun memoryCacheSize(): Int {
        return lruCache.memorySize
    }

    override fun evictOldest(): ByteArray? {
        TODO("Not yet implemented")
    }

    @Synchronized
    override fun clear() {
        lruCache.clear()
        FileCache.clear()
    }
}