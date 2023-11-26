package com.ccb.proandroiddevreader.repository

import com.ccb.proandroiddevreader.service.models.NewsFeedResult

interface NewsFeedRepository {
    suspend fun getNewsFeed(): Result<NewsFeedResult>
}