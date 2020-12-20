package lib.model

import androidx.compose.ui.graphics.ImageBitmap

data class Result(var status: Status, var cause: String, var content: ImageBitmap)