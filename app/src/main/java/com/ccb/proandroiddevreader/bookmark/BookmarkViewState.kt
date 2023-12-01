package com.ccb.proandroiddevreader.bookmark

import com.ccb.proandroiddevreader.feed.models.News

data class BookmarkViewState(
    val bookmarks: List<News> = emptyList(),
)
