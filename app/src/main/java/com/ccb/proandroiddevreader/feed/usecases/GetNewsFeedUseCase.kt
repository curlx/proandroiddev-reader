package com.ccb.proandroiddevreader.feed.usecases

import android.text.format.DateUtils
import com.ccb.proandroiddevreader.repository.NewsFeedRepository
import com.ccb.proandroiddevreader.repository.models.NewsFeed
import kotlinx.datetime.Instant
import java.text.ParseException
import javax.inject.Inject


class GetNewsFeedUseCase @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
) {
    suspend fun getNewsFeed(): Result<NewsFeed> {
        return newsFeedRepository.getNewsFeed().map {
            it.copy(
                news = it.news.map { news ->
                    news.copy(
                        // TODO: handle excess content
                        snippet = "",
                        thumbnail = parseThumbnailUrl(news.snippet) ?: "",
                        published = convertPublishedTime(news.published) ?: "",
                    )
                }
            )
        }
    }

    private fun parseThumbnailUrl(content: String): String? {
        val beginString = "src=\""
        val endString = "\""
        return content.indexOf(beginString).takeIf { it >= 0 }?.let { startIndex ->
            content.indexOf(endString, startIndex + beginString.length).takeIf { it >= 0 }?.let { endIndex ->
                content.subSequence(startIndex + beginString.length, endIndex).toString()
            }
        }
    }

    private fun convertPublishedTime(published: String): String? {
        return try {
            val time = Instant.parse(published).toEpochMilliseconds()
            val now = System.currentTimeMillis()
            DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}