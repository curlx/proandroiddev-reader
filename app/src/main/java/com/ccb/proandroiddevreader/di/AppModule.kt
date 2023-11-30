package com.ccb.proandroiddevreader.di

import android.content.Context
import androidx.room.Room
import com.ccb.proandroiddevreader.repository.BookmarkRepository
import com.ccb.proandroiddevreader.repository.BookmarkRepositoryImpl
import com.ccb.proandroiddevreader.repository.NewsFeedRepository
import com.ccb.proandroiddevreader.repository.NewsFeedRepositoryImpl
import com.ccb.proandroiddevreader.repository.database.AppDatabase
import com.ccb.proandroiddevreader.repository.database.BookmarkDao
import com.ccb.proandroiddevreader.service.HttpClientFactory
import com.ccb.proandroiddevreader.service.NewsFeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClientFactory().create()

    @Provides
    @Singleton
    fun provideNewsFeedRepository(
        newsFeedApi: NewsFeedApi,
    ): NewsFeedRepository {
        return NewsFeedRepositoryImpl(newsFeedApi)
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(
        bookmarkDao: BookmarkDao,
    ): BookmarkRepository {
        return BookmarkRepositoryImpl(bookmarkDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "appdb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(
        appDatabase: AppDatabase,
    ): BookmarkDao {
        return appDatabase.bookmarkDao()
    }
}
