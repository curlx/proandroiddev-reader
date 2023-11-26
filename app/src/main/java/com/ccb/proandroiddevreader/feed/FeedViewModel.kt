package com.ccb.proandroiddevreader.feed

import androidx.lifecycle.ViewModel
import com.ccb.proandroiddevreader.feed.usecases.GetNewsFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getNewsFeedUseCase: GetNewsFeedUseCase,
): ViewModel() {

    private val _state: MutableStateFlow<FeedViewState> = MutableStateFlow(FeedViewState())
    val state: StateFlow<FeedViewState> = _state.asStateFlow()

    suspend fun updateFeed() {
        getNewsFeedUseCase.getNewsFeed()
            .onSuccess { newsList ->
                _state.update {
                    it.copy(news = newsList)
                }
            }
            .onFailure {
                Timber.e(it)
            }
    }
}
