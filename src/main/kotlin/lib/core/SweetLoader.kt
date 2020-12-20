package lib.core

import lib.ImageLoader
import lib.model.Task
import java.net.HttpURLConnection
import java.net.URL
class SweetLoader(override var task: Task) : Loader(task) {
    override fun fetchImage(): ByteArray {
        var imageByteArray: ByteArray
        try {
            val connect = URL(task.url).openConnection() as HttpURLConnection
            connect.requestMethod = "GET"
            connect.doInput = true
            connect.doOutput = false
            connect.connectTimeout = ImageLoader.config.timeout
            connect.connect()
            if (connect.responseCode == 200) {
                imageByteArray = connect.inputStream.readAllBytes()
            } else {
                imageByteArray = byteArrayOf()
            }
        } catch (e: Exception) {
            imageByteArray = byteArrayOf()
        }
        return imageByteArray
    }
}