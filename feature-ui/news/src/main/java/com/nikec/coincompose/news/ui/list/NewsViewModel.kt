package com.nikec.coincompose.news.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nikec.coincompose.data.model.News
import com.nikec.coincompose.domain.usecases.FetchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = PAGE_SIZE

@HiltViewModel
class NewsViewModel @Inject constructor(
    fetchNewsUseCase: FetchNewsUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        enablePlaceholders = true
    )

    private val newsListEventsChannel = Channel<NewsListEvent>(Channel.CONFLATED)
    val newsListEvents: Flow<NewsListEvent> = newsListEventsChannel.receiveAsFlow()

    init {
        fetchNewsUseCase(FetchNewsUseCase.Params(pagingConfig = pagingConfig))
    }

    val paginatedNews = fetchNewsUseCase.flow.cachedIn(viewModelScope)

    fun onNewsClicked(news: News) {
        newsListEventsChannel.trySend(NewsListEvent.OpenDetails(news))
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
    data class OpenDetails(val news: News) : NewsListEvent()
}
