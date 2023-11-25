package com.ccb.proandroiddevreader.feed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FeedScreen(
    feedViewState: FeedViewState,
) {
    LazyColumn {
        items(feedViewState.news) { news ->
            Text(text = news.title)
        }
    }
}