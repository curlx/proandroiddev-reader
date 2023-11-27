package com.ccb.proandroiddevreader.mocks

import com.ccb.proandroiddevreader.feed.models.News

object MockModel {

    val news: News = News(
        title = "title",
        thumbnail = "thumbnail",
        link = "link",
        author = "author",
        published = "published"
    )
}