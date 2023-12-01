package com.ccb.proandroiddevreader.mocks

import com.ccb.proandroiddevreader.feed.models.News

object MockModel {

    val news: News = News(
        guid = "guid",
        title = "title",
        thumbnail = "thumbnail",
        link = "link",
        author = "author",
        published = "published",
        publishedEpochMilli = 1701427755255L,
        isBookmarked = false,
    )
}