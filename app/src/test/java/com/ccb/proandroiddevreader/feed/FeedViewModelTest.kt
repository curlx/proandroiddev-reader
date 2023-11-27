package com.ccb.proandroiddevreader.feed

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.ccb.proandroiddevreader.common.returns
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.feed.usecases.GetNewsFeedUseCase
import com.ccb.proandroiddevreader.mocks.MockModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FeedViewModelTest {

    @Mock
    private lateinit var getNewsFeedUseCase: GetNewsFeedUseCase
    private lateinit var viewModel: FeedViewModel

    @Before
    fun setUp() {
        viewModel = FeedViewModel(getNewsFeedUseCase)
    }

    @Test
    fun `The news list is empty when starting`() = runBlocking {
        viewModel.state.test {
            val result = awaitItem()
            assertThat(result.news).isEmpty()
        }
    }

    @Test
    fun `The news list updates when updating the feed`() = runBlocking {
        getNewsFeedUseCase.getNewsFeed() returns Result.success(listOf(MockModel.news))

        viewModel.state.test {
            awaitItem()

            viewModel.updateFeed()
            assertThat(awaitItem().news).isEqualTo(listOf(MockModel.news))
        }
    }

    @Test
    fun `There is no exception when updating the feed failed`() = runBlocking {
        getNewsFeedUseCase.getNewsFeed() returns Result.failure<List<News>>(Throwable("Fail to fetch new news"))

        viewModel.state.test {
            viewModel.updateFeed()

            assertThat(awaitItem().news).isEqualTo(emptyList())
        }
    }
}
