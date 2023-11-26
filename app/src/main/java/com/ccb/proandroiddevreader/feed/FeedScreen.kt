package com.ccb.proandroiddevreader.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.ui.theme.ProAndroidDevReaderTheme

@Composable
fun FeedScreen(
    feedViewState: FeedViewState,
    modifier: Modifier = Modifier,
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
                modifier = Modifier.fillMaxWidth(),
            ) {
                AsyncImage(
                    modifier = Modifier.height(160.dp),
                    model = news.thumbnail,
                    contentDescription = news.title,
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp),
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "By ${news.author}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = news.published,
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
            )
        )
    }
}
