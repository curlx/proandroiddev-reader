package com.ccb.proandroiddevreader.repository

import com.ccb.proandroiddevreader.repository.extensions.toNewsFeed
import com.ccb.proandroiddevreader.repository.models.NewsFeed
import com.ccb.proandroiddevreader.service.NewsFeedApi
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val newsFeedApi: NewsFeedApi,
) : NewsFeedRepository {

    override suspend fun getNewsFeed(): Result<NewsFeed> {
        return newsFeedApi.fetchNewsFeed().map { it.toNewsFeed() }
    }
}