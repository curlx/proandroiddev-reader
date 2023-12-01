package com.ccb.proandroiddevreader.mocks

import com.ccb.proandroiddevreader.bookmark.usecases.HandleBookmarksUseCase
import com.ccb.proandroiddevreader.feed.models.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeHandleBookmarksUseCase : HandleBookmarksUseCase {

    private val _data = MutableStateFlow<List<News>>(emptyList())

    override fun getAllBookmarks(): Flow<List<News>> {
        return _data.asStateFlow()
    }

    override suspend fun addBookmark(news: News) {
        _data.value += news
    }

    override suspend fun removeBookmark(newsGuid: String) {
        _data.value = _data.value.filter { it.guid != newsGuid }
    }
}
