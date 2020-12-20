package lib.cache

import java.io.File

class CacheInfoRecord(
    val url: String,
    val cachedFile: File,
    var lastAccessTime: Long,
)
