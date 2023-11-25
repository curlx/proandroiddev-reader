package com.ccb.proandroiddevreader.repository

import com.ccb.proandroiddevreader.repository.models.NewsFeed

interface NewsFeedRepository {
    suspend fun getNewsFeed(): Result<NewsFeed>
}