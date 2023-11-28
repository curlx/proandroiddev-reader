package com.ccb.proandroiddevreader.feed

import com.ccb.proandroiddevreader.feed.models.News

data class FeedViewState(
    val news: List<News> = emptyList(),
    val isRefreshing: Boolean = false,
)