package com.ccb.proandroiddevreader.feed.usecases

import com.ccb.proandroiddevreader.repository.NewsFeedRepository
import com.ccb.proandroiddevreader.repository.models.NewsFeed
import javax.inject.Inject

class GetNewsFeedUseCase @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
) {
    suspend fun getNewsFeed(): Result<NewsFeed> {
        return newsFeedRepository.getNewsFeed()
    }
}