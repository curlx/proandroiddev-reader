package com.ccb.proandroiddevreader.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.ui.theme.ProAndroidDevReaderTheme

@Composable
fun FeedScreen(
    feedViewState: FeedViewState,
    modifier: Modifier = Modifier,
    onSelectedNews: (News) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = feedViewState.news,
            key = { it.title },
        ) { news ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectedNews(news) }
            ) {
                ConstraintLayout() {
                    val (thumbnail, title, info) = createRefs()

                    AsyncImage(
                        modifier = Modifier
                            .height(160.dp)
                            .constrainAs(thumbnail) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        model = news.thumbnail,
                        contentDescription = news.title,
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .constrainAs(title) {
                                top.linkTo(thumbnail.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                        text = news.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(RoundedCornerShape(16.dp, 0.dp, 0.dp, 0.dp))
                            .background(Color.White.copy(alpha = 0.7f))
                            .padding(8.dp)
                            .constrainAs(info) {
                                bottom.linkTo(thumbnail.bottom)
                                end.linkTo(thumbnail.end)
                            },
                        text = "Published ${news.published} by ${news.author}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    ProAndroidDevReaderTheme {
        FeedScreen(
            feedViewState = FeedViewState(
                news = listOf(
                    // TODO: Preview image when using coil?
                    News(
                        title = "Dive into Kotlin Coroutines",
                        thumbnail = "https://miro.medium.com/v2/resize:fit:720/format:webp/0*h1bruWVL-782e6V_",
                        author = "Nek.12",
                        published = "1 day ago",
                        link = "https://proandroiddev.com/dive-into-kotlin-coroutines-6f95dc4b7bf6?source=rss----c72404660798---4",
                    )
                )
            ),
            onSelectedNews = {},
        )
    }
}
