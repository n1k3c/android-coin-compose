package com.nikec.coincompose.news.ui.list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.compose.collectAsLazyPagingItems
import com.nikec.coincompose.core.extensions.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val newsList = viewModel.paginatedNews.collectAsLazyPagingItems()
    val newsListEvents = rememberFlowWithLifecycle(flow = viewModel.newsListEvents)

    LaunchedEffect(newsListEvents) {
        newsListEvents.collect {
            when (it) {
                NewsListEvent.Refresh -> newsList.refresh()
                NewsListEvent.Retry -> newsList.retry()
            }
        }
    }

    Scaffold {
        NewsContent(
            newsList = newsList,
            onRefresh = viewModel::onRefresh,
            onNewsClicked = viewModel::onNewsClicked,
            onRetryClicked = viewModel::onRetryClicked
        )
    }
}
