package com.ccb.proandroiddevreader.repository

import com.ccb.proandroiddevreader.repository.database.BookmarkDao
import com.ccb.proandroiddevreader.repository.database.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
) : BookmarkRepository {

    override fun getAllBookmarks(): Flow<List<NewsEntity>> {
        return bookmarkDao.getBookmarks().distinctUntilChanged()
    }

    override suspend fun addBookmark(newsEntity: NewsEntity) {
        withContext(Dispatchers.IO) {
            bookmarkDao.insertNews(newsEntity)
        }
    }

    override suspend fun removeBookmark(newsGuid: String) {
        withContext(Dispatchers.IO) {
            bookmarkDao.deleteNews(newsGuid)
        }
    }
}