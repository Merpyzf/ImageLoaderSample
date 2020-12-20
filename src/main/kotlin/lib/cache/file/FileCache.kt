package lib.cache.file

import lib.ImageLoader
import java.io.*

class FileCache {
    companion object {
        @Synchronized
        fun put(key: String, content: ByteArray): Boolean {
            val dir = File(ImageLoader.config.diskCacheTarget)
            if (!dir.exists()) {
                dir.mkdirs()
            }

            val bos = BufferedOutputStream(FileOutputStream(File(dir, key)))
            bos.write(content, 0, content.size)
            bos.close()
            return true
        }

        @Synchronized
        fun get(key: String): ByteArray? {
            val file = File(ImageLoader.config.diskCacheTarget, key)
            if (!file.exists()) {
                return null
            }
            val bis = BufferedInputStream(FileInputStream(file))
            val content = bis.readAllBytes()
            bis.close()
            return content
        }

        @Synchronized
        fun exists(key: String): Boolean {
            return File(ImageLoader.config.diskCacheTarget, key).exists()
        }

        @Synchronized
        fun size(): Int {
            val file = File(ImageLoader.config.diskCacheTarget)
            if (file.exists()) {
                val listFiles = file.listFiles()
                if (listFiles != null) {
                    return listFiles.size
                }
            }
            return 0
        }

        @Synchronized
        fun clear() {
            val file = File(ImageLoader.config.diskCacheTarget)
            if (file.exists()) {
                file.listFiles()?.let {
                    it.forEach {
                        it.deleteOnExit()
                    }
                }
            }
        }
    }
}