package com.nikec.coincompose.news.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.news.data.model.News
import com.nikec.coincompose.news.domain.FetchCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    fetchCoinsUseCase: FetchCoinsUseCase
) : ViewModel() {

    private val newsListEventsChannel = Channel<NewsListEvent>(Channel.CONFLATED)
    val newsListEvents: Flow<NewsListEvent> = newsListEventsChannel.receiveAsFlow()

    val paginatedNews = fetchCoinsUseCase.execute().cachedIn(viewModelScope)

    fun onNewsClicked(news: News) {
        i { "News: $news" }
    }

    fun onRefresh() {
        newsListEventsChannel.trySend(NewsListEvent.Refresh)
    }

    fun onRetryClicked() {
        newsListEventsChannel.trySend(NewsListEvent.Retry)
    }
}

sealed class NewsListEvent {
    object Refresh : NewsListEvent()
    object Retry : NewsListEvent()
}
