package com.ccb.proandroiddevreader.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccb.proandroiddevreader.R
import com.ccb.proandroiddevreader.feed.NewsList
import com.ccb.proandroiddevreader.feed.models.News

@Composable
fun BookmarkScreen(
    bookmarksViewState: BookmarkViewState,
    onSelectedNews: (News) -> Unit,
    onToggleBookmark: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    if(bookmarksViewState.bookmarks.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = stringResource(R.string.there_is_no_bookmarked_news),
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = stringResource(R.string.there_is_no_bookmarked_news),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    } else {
        NewsList(
            news = bookmarksViewState.bookmarks,
            onSelectedNews = onSelectedNews,
            onToggleBookmark = onToggleBookmark,
            modifier = modifier,
        )
    }
}
