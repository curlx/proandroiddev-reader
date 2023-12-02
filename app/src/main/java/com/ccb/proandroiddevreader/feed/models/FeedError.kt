package com.ccb.proandroiddevreader.feed.models

enum class FeedError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR,
}

class FeedException(val error: FeedError): Exception(
    "An error occurred when fetching news: $error"
)