package com.ccb.proandroiddevreader.bookmark.usecases

import com.ccb.proandroiddevreader.extensions.toNews
import com.ccb.proandroiddevreader.extensions.toNewsEntity
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant
import javax.inject.Inject

class HandleBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {
    fun getAllBookmarks(): Flow<List<News>> =
        bookmarkRepository.getAllBookmarks()
            .map {
                val now = Instant.now().toEpochMilli()
                it.map { newsEntity ->
                    newsEntity.toNews(now).copy(isBookmarked = true)
                }
            }

    suspend fun addBookmark(news: News) {
        bookmarkRepository.addBookmark(news.toNewsEntity())
    }

    suspend fun removeBookmark(newsGuid: String) {
        bookmarkRepository.removeBookmark(newsGuid)
    }
}
