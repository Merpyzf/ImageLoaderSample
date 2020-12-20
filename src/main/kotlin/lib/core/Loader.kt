package lib.core

import lib.model.Task

abstract class Loader(open var task: Task) {
    init {
        fetchImage()
    }

    abstract fun fetchImage(): ByteArray

}