package com.ccb.proandroiddevreader.service

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
            Result.success(result.body())
        } catch (t: Throwable) {
            // TODO: create self exception
            Timber.e(t)
            Result.failure(t)
        }
    }

    companion object {
        private const val BASE_URL: String = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fproandroiddev.com%2Ffeed"
    }
}