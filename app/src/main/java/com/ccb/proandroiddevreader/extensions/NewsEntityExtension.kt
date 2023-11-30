package com.ccb.proandroiddevreader.extensions

import android.text.format.DateUtils
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.repository.database.NewsEntity
import timber.log.Timber

fun NewsEntity.toNews(now: Long): News {
    return News(
        guid = guid,
        title = title,
        thumbnail = thumbnail,
        link = link,
        author = author,
        published = convertPublishedTime(published, now) ?: "",
        publishedEpochMilli = published,
    )
}

fun News.toNewsEntity(): NewsEntity {
    return NewsEntity(
        guid = guid,
        title = title,
        thumbnail = thumbnail,
        link = link,
        author = author,
        published = publishedEpochMilli,
    )
}

private fun convertPublishedTime(published: Long, now: Long): String? =
    try {
        DateUtils.getRelativeTimeSpanString(published, now, DateUtils.MINUTE_IN_MILLIS).toString()
    } catch (t: Throwable) {
        Timber.e(t)
        null
    }