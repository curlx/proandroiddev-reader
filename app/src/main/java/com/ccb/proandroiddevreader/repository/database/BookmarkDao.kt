package com.ccb.proandroiddevreader.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM news")
    fun getBookmarks(): Flow<List<NewsEntity>>

    @Insert
    fun insertNews(vararg news: NewsEntity)

    @Query("DELETE FROM news WHERE guid = :newsGuid")
    fun deleteNews(newsGuid: String)
}
