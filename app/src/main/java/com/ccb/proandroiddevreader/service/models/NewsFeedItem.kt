package com.ccb.proandroiddevreader.service.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsFeedItem(
    @SerialName("title") val title: String,
    @SerialName("link") val link: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: String,
    @SerialName("pubDate") val published: String,
)
