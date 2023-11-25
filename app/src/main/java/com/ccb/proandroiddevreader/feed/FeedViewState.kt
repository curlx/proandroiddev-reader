package com.ccb.proandroiddevreader.feed

import com.ccb.proandroiddevreader.repository.models.News

data class FeedViewState(
    val news: List<News> = emptyList(),
)