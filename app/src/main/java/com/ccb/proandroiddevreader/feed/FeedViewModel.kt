package com.ccb.proandroiddevreader.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccb.proandroiddevreader.R
import com.ccb.proandroiddevreader.bookmark.usecases.HandleBookmarksUseCase
import com.ccb.proandroiddevreader.feed.extensions.toErrorMessage
import com.ccb.proandroiddevreader.feed.models.FeedException
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.feed.usecases.GetNewsFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getNewsFeedUseCase: GetNewsFeedUseCase,
    private val handleBookmarksUseCase: HandleBookmarksUseCase,
) : ViewModel() {

    private var isRequestedInitialUpdate: Boolean = false
    private val bookmarkedNewsGuid: Flow<HashSet<String>> = handleBookmarksUseCase.getAllBookmarks()
        .map { it.map { news -> news.guid }.toHashSet() }

    private val _state: MutableStateFlow<FeedViewState> = MutableStateFlow(FeedViewState())
    val state: StateFlow<FeedViewState> =
        combine(_state, bookmarkedNewsGuid) { state, bookmarkedNewsGuid ->
            state.copy(
                news = state.news.map { news ->
                    news.copy(isBookmarked = bookmarkedNewsGuid.contains(news.guid))
                },
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), FeedViewState())

    fun requestInitialUpdate() {
        if (!isRequestedInitialUpdate) {
            updateFeed()
            isRequestedInitialUpdate = true
        }
    }
    fun updateFeed() {
        _state.update { it.copy(isRefreshing = true, errorStringRes = null) }
        viewModelScope.launch {
            getNewsFeedUseCase.getNewsFeed()
                .onSuccess { newsList ->
                    _state.update { feedViewState ->
                        feedViewState.copy(
                            news = newsList,
                            isRefreshing = false,
                            errorStringRes = null,
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)
                    _state.update { feedViewState ->
                        feedViewState.copy(
                            isRefreshing = false,
                            errorStringRes = (throwable as? FeedException)?.error?.toErrorMessage() ?: R.string.unknown_error,
                        )
                    }
                }
        }
    }

    fun toggleNewsBookmark(news: News) {
        viewModelScope.launch {
            try {
                if (news.isBookmarked) {
                    handleBookmarksUseCase.removeBookmark(news.guid)
                } else {
                    handleBookmarksUseCase.addBookmark(news)
                }
            } catch (t: Throwable) {
                if (t is CancellationException) {
                    throw t
                }
                Timber.e(t)
            }
        }
    }
}
