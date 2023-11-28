package com.ccb.proandroiddevreader.feed.extensions

import android.text.format.DateUtils
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.service.models.NewsFeedItem
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber

private val dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun NewsFeedItem.toNews(now: Long): News =
    News(
        title = title,
        thumbnail = parseThumbnailUrl(content) ?: "",
        link = link,
        author = author,
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

private fun convertPublishedTime(published: String, now: Long): String? =
    try {
        val time = LocalDateTime.parse(published, dateTimeFormat).toInstant(ZoneOffset.UTC).toEpochMilli()
        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString()
    } catch (t: Throwable) {
        Timber.e(t)
        null
    }
