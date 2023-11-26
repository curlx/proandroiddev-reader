package com.ccb.proandroiddevreader.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.ccb.proandroiddevreader.R
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.ui.theme.ProAndroidDevReaderTheme
import com.ccb.proandroiddevreader.ui.theme.WhiteAlpha

@Composable
fun FeedScreen(
    feedViewState: FeedViewState,
    modifier: Modifier = Modifier,
    onSelectedNews: (News) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.articles),
            style = MaterialTheme.typography.titleLarge,
        )
        NewsList(
            news = feedViewState.news,
            onSelectedNews = onSelectedNews,
        )
    }
}

@Composable
fun NewsList(
    news: List<News>,
    onSelectedNews: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = news,
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
                            .wrapContentWidth()
                            .padding(16.dp)
                            .constrainAs(title) {
                                top.linkTo(thumbnail.bottom)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            },
                        text = news.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(RoundedCornerShape(16.dp, 0.dp, 0.dp, 0.dp))
                            .background(WhiteAlpha)
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .constrainAs(info) {
                                bottom.linkTo(thumbnail.bottom)
                                end.linkTo(thumbnail.end)
                            },
                        text = stringResource(R.string.published_by, news.published, news.author),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
        item {
            Spacer(Modifier.height(0.dp))
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
