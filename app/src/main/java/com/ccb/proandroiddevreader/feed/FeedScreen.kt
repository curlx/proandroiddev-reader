package com.ccb.proandroiddevreader.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.ccb.proandroiddevreader.R
import com.ccb.proandroiddevreader.extensions.lazyListItemPosition
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.ui.theme.ProAndroidDevReaderTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(
    feedViewState: FeedViewState,
    modifier: Modifier = Modifier,
    onSelectedNews: (News) -> Unit,
    onRefresh: () -> Unit,
) {
    val refreshState = rememberPullRefreshState(feedViewState.isRefreshing, onRefresh)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .pullRefresh(refreshState),
    ) {
        AnimatedVisibility(visible = feedViewState.news.isNotEmpty()) {
            NewsList(
                news = feedViewState.news,
                onSelectedNews = onSelectedNews,
            )
        }
        PullRefreshIndicator(feedViewState.isRefreshing, refreshState, Modifier.align(TopCenter))
    }
}

@Composable
fun NewsList(
    news: List<News>,
    onSelectedNews: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .testTag("newsList"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = news,
            key = { _, news -> news.title },
        ) { index, news ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectedNews(news) }
                    .lazyListItemPosition(index)
            ) {
                ConstraintLayout {
                    val (thumbnail, title, info, bookmark) = createRefs()

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
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(start = 16.dp)
                            .testTag("title")
                            .constrainAs(title) {
                                top.linkTo(thumbnail.bottom)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(bookmark.start)
                            },
                        text = news.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(32.dp)
                            .constrainAs(bookmark) {
                                top.linkTo(title.top)
                                bottom.linkTo(title.bottom)
                                end.linkTo(parent.end)
                            },
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = stringResource(R.string.bookmark),
                    )
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(RoundedCornerShape(16.dp, 0.dp, 0.dp, 0.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .constrainAs(info) {
                                bottom.linkTo(thumbnail.bottom)
                                end.linkTo(thumbnail.end)
                            }
                            .testTag("information"),
                        text = stringResource(R.string.published_by, news.published, news.author),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
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
            onRefresh = {},
        )
    }
}
