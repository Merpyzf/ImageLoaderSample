package lib

import lib.core.TaskDispatcher
import lib.core.TaskQueue
import lib.model.Config
import lib.model.Result
import lib.model.Task

class ImageLoader {
    var imgUrl: String = ""
    var downloadUrl: String = ""
    var targetDir: String = ""
    var resultListener: ((Result) -> Unit)? = null
    init {
        TaskDispatcher().start()
        println("TaskDispatcher().start()")
    }

    companion object {
        val config: Config by lazy { Config() }
        val instance: ImageLoader by lazy { ImageLoader() }
    }


    fun loadUrl(url: String): ImageLoader {
        this.imgUrl = url
        this.downloadUrl = ""
        this.targetDir = ""
        return this
    }

    fun download(url: String, target: String): ImageLoader {
        this.downloadUrl = url
        this.targetDir = targetDir
        this.imgUrl = ""
        return this
    }

    fun timeout(second: Int): ImageLoader {
        ImageLoader.config.timeout = second * 1000
        return this
    }

    fun enableDiskCache(enable: Boolean): ImageLoader {
        ImageLoader.config.enableDiskCache = enable
        return this
    }

    fun diskCacheTarget(target: String): ImageLoader {
        ImageLoader.config.diskCacheTarget = target
        return this
    }

    fun listen(resultListener: (result: Result) -> Unit): ImageLoader {
        this.resultListener = resultListener
        return this
    }

    fun exec(): ImageLoader {
        TaskQueue.instance.add(Task.convertToTask(this))
        return this
    }
}