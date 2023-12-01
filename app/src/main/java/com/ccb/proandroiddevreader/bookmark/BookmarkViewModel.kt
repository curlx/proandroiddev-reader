package com.ccb.proandroiddevreader.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccb.proandroiddevreader.bookmark.usecases.HandleBookmarksUseCase
import com.ccb.proandroiddevreader.feed.models.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarksUseCase: HandleBookmarksUseCase,
) : ViewModel() {

    val state: StateFlow<BookmarkViewState> = bookmarksUseCase.getAllBookmarks()
        .map { BookmarkViewState(bookmarks = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), BookmarkViewState())

    fun toggleNewsBookmark(news: News) {
        viewModelScope.launch {
            try {
                if (news.isBookmarked) {
                    bookmarksUseCase.removeBookmark(news.guid)
                } else {
                    bookmarksUseCase.addBookmark(news)
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
