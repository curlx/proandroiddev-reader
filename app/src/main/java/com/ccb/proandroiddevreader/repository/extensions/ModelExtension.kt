package com.ccb.proandroiddevreader.repository.extensions

import com.ccb.proandroiddevreader.repository.models.News
import com.ccb.proandroiddevreader.repository.models.NewsFeed
import com.ccb.proandroiddevreader.service.models.NewsFeedResult

fun NewsFeedResult.toNewsFeed(): NewsFeed =
    NewsFeed(
        news = items.map { newsItem ->
            News(
                title = newsItem.title,
                thumbnail = "",
                snippet = newsItem.content,
                author = newsItem.author.name,
                published = newsItem.published.toString(),
                link = newsItem.url,
            )
        }
    )