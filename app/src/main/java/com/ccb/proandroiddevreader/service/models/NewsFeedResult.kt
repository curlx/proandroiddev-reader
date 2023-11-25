package com.ccb.proandroiddevreader.service.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsFeedResult(
    @SerialName("items") val items: List<NewsFeedItem>,
)