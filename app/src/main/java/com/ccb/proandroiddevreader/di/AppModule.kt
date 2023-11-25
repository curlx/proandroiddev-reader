package com.ccb.proandroiddevreader.di

import com.ccb.proandroiddevreader.repository.NewsFeedRepository
import com.ccb.proandroiddevreader.repository.NewsFeedRepositoryImpl
import com.ccb.proandroiddevreader.service.HttpClientFactory
import com.ccb.proandroiddevreader.service.NewsFeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}
