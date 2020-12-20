### 🖥️ 为 Compose for Desktop 编写的从 URL 中加载图片并进行展示的示例

#### 运行效果

![](screenshot/screenshot.png)

#### 基于ImageLoader封装的可用加载网络图片的组件
```
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
```
#### 实现的功能

1. 网络图片加载基于生产者消费者线程模型
2. 使用 HttpUrlConnection 进行图片下载
3. 三级缓存，依次从内存 -> 本地磁盘 -> 网络 加载图片

#### 配合 LazyColumnFor 使用时存在的问题
1. 滚动时会出现明显的卡顿问题（猜测是由于在组件内部执行 onCommit 异步请求所导致）
2. 已滚出屏幕外的 View 所占用的内存不会及时释放，容易造成内存溢出

