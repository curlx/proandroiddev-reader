package com.ccb.proandroiddevreader.service

import com.ccb.proandroiddevreader.feed.models.FeedError
import com.ccb.proandroiddevreader.feed.models.FeedException
import com.ccb.proandroiddevreader.service.models.NewsFeedResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import timber.log.Timber
import javax.inject.Inject

class NewsFeedApi @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun fetchNewsFeed(): Result<NewsFeedResult> {
        return try {
            val result = httpClient.get(BASE_URL) {
                contentType(ContentType.Application.Json)
            }

            when(result.status.value) {
                in 200..299 -> Unit
                in 400..499 -> throw FeedException(FeedError.CLIENT_ERROR)
                500 -> throw FeedException(FeedError.SERVER_ERROR)
                else -> throw FeedException(FeedError.UNKNOWN_ERROR)
            }

            Result.success(result.body())
        } catch (t: Throwable) {
            Result.failure(FeedException(FeedError.SERVICE_UNAVAILABLE))
        }
    }

    companion object {
        private const val BASE_URL: String = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fproandroiddev.com%2Ffeed"
    }
}