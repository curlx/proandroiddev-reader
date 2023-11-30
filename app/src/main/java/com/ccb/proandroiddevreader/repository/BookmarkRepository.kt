package com.ccb.proandroiddevreader.repository

import com.ccb.proandroiddevreader.repository.database.NewsEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getAllBookmarks(): Flow<List<NewsEntity>>
    suspend fun addBookmark(newsEntity: NewsEntity)
    suspend fun removeBookmark(newsGuid: String)
}
