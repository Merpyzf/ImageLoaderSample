import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import lib.ImageLoader
import lib.model.Status

fun main() = Window(title = "ImageLoaderSample") {
    App()
}

@Composable
fun App() {
    val images = getImages()
    MaterialTheme {
        LazyGridFor(images, 8) {
            NetworkImage(
                it,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .padding(10.dp)
                    .align(Alignment.Center)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
fun <T> LazyGridFor(
    items: List<T>,
    rowSize: Int = 1,
    itemContent: @Composable BoxScope.(T) -> Unit
) {
    val rows = items.chunked(rowSize)
    LazyColumnForIndexed(rows) { index, row ->
        Row(Modifier.fillParentMaxWidth(1f)) {
            for ((index, item) in row.withIndex()) {
                Box(Modifier.fillMaxWidth(1f / (rowSize - index))) {
                    itemContent(item)
                }
            }
        }
    }
}


@Composable
fun NetworkImage(url: String, modifier: Modifier) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    imageBitmap?.let {
        Image(bitmap = imageBitmap!!, modifier = modifier, contentScale = ContentScale.Crop)
    }
    onCommit(url) {
        ImageLoader.instance
            .loadUrl(url)
            .listen {
                if (it.status == Status.success) {
                    imageBitmap = it.content
                } else {
                    println("图片加载失败")
                }
            }
            .exec()
    }
}

fun getImages(): MutableList<String> {
    val images = mutableListOf<String>()
    images.add("https://img1.doubanio.com/view/subject/l/public/s33749759.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33749274.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33763757.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33766859.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33766605.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29793550.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29053580.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s24514468.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1103152.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1727290.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1070959.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s27237850.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29651121.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s29179365.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s3254244.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1144911.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1070222.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s29634528.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s11284102.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33642427.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s4371408.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s23128183.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s2768378.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s32266692.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s30016152.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1146040.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s24575140.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1595557.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33749759.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33749274.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33763757.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33766859.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33766605.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29793550.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29053580.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s24514468.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1103152.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1727290.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1070959.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s27237850.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29651121.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s29179365.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s3254244.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1144911.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1070222.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s29634528.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s11284102.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33642427.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s4371408.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s23128183.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s2768378.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s32266692.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s30016152.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1146040.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s24575140.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1595557.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33749759.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33749274.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33763757.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33766859.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33766605.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29793550.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29053580.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s24514468.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1103152.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1727290.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1070959.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s27237850.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29651121.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s29179365.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s3254244.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1144911.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1070222.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s29634528.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s11284102.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33642427.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s4371408.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s23128183.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s2768378.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s32266692.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s30016152.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1146040.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s24575140.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1595557.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33749759.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33749274.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33763757.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33766859.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33766605.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29793550.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29053580.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s24514468.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1103152.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1727290.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1070959.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s27237850.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s29651121.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s29179365.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s3254244.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1144911.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1070222.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s29634528.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s11284102.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s33642427.jpg")
    images.add("https://img9.doubanio.com/view/subject/l/public/s33759576.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s4371408.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s23128183.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s2768378.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s32266692.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s30016152.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s1146040.jpg")
    images.add("https://img3.doubanio.com/view/subject/l/public/s24575140.jpg")
    images.add("https://img1.doubanio.com/view/subject/l/public/s1595557.jpg")
    return images
}


