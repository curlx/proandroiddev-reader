package com.ccb.proandroiddevreader.service

import com.ccb.proandroiddevreader.service.models.NewsFeedResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
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
        } catch (e: Exception) {
            // TODO: create self exception
            e.printStackTrace()
            Result.failure(Exception("Server error"))
        }
    }

    companion object {
        private const val BASE_URL: String = "https://www.toptal.com/developers/feed2json/convert?url=https%3A%2F%2Fproandroiddev.com%2Ffeed"
    }
}