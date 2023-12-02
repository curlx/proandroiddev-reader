package com.ccb.proandroiddevreader.feed.extensions

import androidx.annotation.StringRes
import com.ccb.proandroiddevreader.R
import com.ccb.proandroiddevreader.feed.models.FeedError

@StringRes
fun FeedError.toErrorMessage(): Int =
    when(this) {
        FeedError.SERVICE_UNAVAILABLE -> R.string.service_unavailable
        FeedError.CLIENT_ERROR -> R.string.client_error
        FeedError.SERVER_ERROR -> R.string.server_error
        FeedError.UNKNOWN_ERROR -> R.string.unknown_error
    }
