package com.ccb.proandroiddevreader.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccb.proandroiddevreader.feed.usecases.GetNewsFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getNewsFeedUseCase: GetNewsFeedUseCase,
): ViewModel() {

    private val _state: MutableStateFlow<FeedViewState> = MutableStateFlow(FeedViewState())
    val state: StateFlow<FeedViewState> = _state.asStateFlow()

    fun updateFeed() {
        _state.update { it.copy(isRefreshing = true) }
        viewModelScope.launch {
            getNewsFeedUseCase.getNewsFeed()
                .onSuccess { newsList ->
                    _state.update { feedViewState ->
                        feedViewState.copy(
                            news = newsList,
                            isRefreshing = false,
                        )
                    }
                }
                .onFailure {
                    Timber.e(it)
                    _state.update { feedViewState ->
                        feedViewState.copy(isRefreshing = false)
                    }
                }
        }
    }
}
