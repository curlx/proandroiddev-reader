package com.ccb.proandroiddevreader.service.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsFeedItem(
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("content_html") val content: String,
    @SerialName("author") val author: Author,
    @SerialName("date_published") val published: Instant,
) {
    @Serializable
    data class Author(
        @SerialName("name") val name: String,
    )
}
