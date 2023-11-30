package com.ccb.proandroiddevreader.feed.models

data class News(
    val guid: String,
    val title: String,
    val thumbnail: String,
    val link: String,
    val author: String,
    val published: String,
    val publishedEpochMilli: Long,
    val isBookmarked: Boolean = false,
)
