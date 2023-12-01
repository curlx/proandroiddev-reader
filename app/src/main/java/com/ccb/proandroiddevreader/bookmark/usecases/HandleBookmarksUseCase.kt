package com.ccb.proandroiddevreader.bookmark.usecases

import com.ccb.proandroiddevreader.feed.models.News
import kotlinx.coroutines.flow.Flow

interface HandleBookmarksUseCase {
    fun getAllBookmarks(): Flow<List<News>>
    suspend fun addBookmark(news: News)
    suspend fun removeBookmark(newsGuid: String)
}
