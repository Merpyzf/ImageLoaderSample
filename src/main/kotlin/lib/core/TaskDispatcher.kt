package lib.core

import androidx.compose.ui.graphics.asImageBitmap
import lib.ImageLoader
import lib.model.Result
import lib.model.Status
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TaskDispatcher : Thread() {
    private var taskQueue = TaskQueue.instance
    private var cacheStrategy = ImageLoader.config.cacheStrategy
    private val executor: ExecutorService = Executors.newFixedThreadPool(10)
    override fun run() {
        while (!interrupted()) {
            val task = taskQueue.take()
            executor.run {
                if (cacheStrategy.contains(task.url) && cacheStrategy.get(task.url) != null) {
                    val imageBytes = cacheStrategy.get(task.url)!!
                    task.resultCallback?.let {
                        it(
                            Result(
                                Status.success,
                                "load from cache",
                                org.jetbrains.skija.Image.makeFromEncoded(imageBytes).asImageBitmap()
                            )
                        )
                    }
                } else {
                    val imageBytes = SweetLoader(task).fetchImage()
                    if (imageBytes.isNotEmpty()) {
                        task.resultCallback?.let {
                            it(
                                Result(
                                    Status.success,
                                    "load from network",
                                    org.jetbrains.skija.Image.makeFromEncoded(imageBytes).asImageBitmap()
                                )
                            )
                        }
                        // cache
                        cacheStrategy.store(task.url, imageBytes)
                    } else {
                        task.resultCallback?.let {
                            it(
                                Result(
                                    Status.failed,
                                    "load from network failed",
                                    org.jetbrains.skija.Image.makeFromEncoded(imageBytes).asImageBitmap()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}