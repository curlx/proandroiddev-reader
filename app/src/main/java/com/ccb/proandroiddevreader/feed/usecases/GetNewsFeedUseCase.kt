package com.ccb.proandroiddevreader.feed.usecases

import com.ccb.proandroiddevreader.extensions.toNews
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.repository.NewsFeedRepository
import org.threeten.bp.Instant
import javax.inject.Inject

class GetNewsFeedUseCase @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
) {
    suspend fun getNewsFeed(): Result<List<News>> {
        return newsFeedRepository.getNewsFeed().map {
            val now = Instant.now().toEpochMilli()
            it.items.map { newsFeedItem ->
                newsFeedItem.toNews(now)
            }
        }
    }
}
