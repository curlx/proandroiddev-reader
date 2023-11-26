package com.ccb.proandroiddevreader.repository

import com.ccb.proandroiddevreader.service.NewsFeedApi
import com.ccb.proandroiddevreader.service.models.NewsFeedResult
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val newsFeedApi: NewsFeedApi,
) : NewsFeedRepository {

    override suspend fun getNewsFeed(): Result<NewsFeedResult> =
        newsFeedApi.fetchNewsFeed()
}
