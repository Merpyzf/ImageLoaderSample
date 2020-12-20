package lib.model

import lib.cache.Cache
import lib.cache.DefaultCache

data class Config(
    var timeout: Int = 5000,
    var enableDiskCache: Boolean = true,
    var diskCacheTarget: String = "/Users/wangke/Downloads/ComposeImageCache",
    var cacheStrategy: Cache<String, ByteArray> = DefaultCache()
)