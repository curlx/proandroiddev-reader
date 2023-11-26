package com.ccb.proandroiddevreader.feed.extensions

import android.text.format.DateUtils
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.service.models.NewsFeedItem
import kotlinx.datetime.Instant
import timber.log.Timber
import java.text.ParseException

fun NewsFeedItem.toNews(now: Long): News =
    News(
        title = title,
        thumbnail = parseThumbnailUrl(content) ?: "",
        link = url,
        author = author.name,
        published = convertPublishedTime(published, now) ?: "",
    )

private fun parseThumbnailUrl(content: String): String? {
    val beginString = "src=\""
    val endString = "\""
    return content.indexOf(beginString).takeIf { it >= 0 }?.let { startIndex ->
        content.indexOf(endString, startIndex + beginString.length).takeIf { it >= 0 }?.let { endIndex ->
            content.subSequence(startIndex + beginString.length, endIndex).toString()
        }
    }
}

private fun convertPublishedTime(published: Instant, now: Long): String? =
    try {
        val time = published.toEpochMilliseconds()
        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString()
    } catch (e: ParseException) {
        Timber.e(e)
        null
    }
