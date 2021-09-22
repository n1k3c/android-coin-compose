package com.nikec.coincompose.news.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikec.coincompose.core.ui.atoms.AppendLoadingIndicator
import com.nikec.coincompose.core.ui.atoms.ConnectivityStatus
import com.nikec.coincompose.core.ui.atoms.ErrorStatus
import com.nikec.coincompose.core.ui.atoms.SwipeToRefreshIndicator
import com.nikec.coincompose.news.data.model.News

@Composable
fun NewsContent(
    newsList: LazyPagingItems<News>,
    onRefresh: () -> Unit,
    onNewsClicked: (News) -> Unit,
    onRetryClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConnectivityStatus()
        NewsList(
            newsList = newsList,
            onRefresh = onRefresh,
            onNewsClicked = onNewsClicked,
            onRetryClicked = onRetryClicked
        )
    }
}

@Composable
private fun NewsList(
    newsList: LazyPagingItems<News>,
    onRefresh: () -> Unit,
    onNewsClicked: (News) -> Unit,
    onRetryClicked: () -> Unit
) {
    Box {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = newsList.loadState.refresh is LoadState.Loading),
            onRefresh = { onRefresh() },
            indicator = { state, trigger ->
                SwipeToRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger
                )
            }) {
            LazyColumn {
                items(newsList) { news ->
                    news?.let {
                        NewsItem(
                            news = news,
                            onNewsClicked = onNewsClicked
                        )
                    }
                }

                newsList.apply {
                    if (loadState.append is LoadState.Loading) {
                        item {
                            AppendLoadingIndicator()
                        }
                    }
                    if (loadState.append is LoadState.Error) {
                        item {
                            ErrorStatus(throwable = (loadState.append as LoadState.Error).error) {
                                onRetryClicked()
                            }
                        }
                    }
                }
            }
        }

        if (newsList.loadState.refresh is LoadState.Error && newsList.snapshot().isEmpty()) {
            ErrorStatus(throwable = (newsList.loadState.refresh as LoadState.Error).error) {
                onRetryClicked()
            }
        }
    }
}

@Composable
private fun NewsItem(news: News, onNewsClicked: (News) -> Unit) {
    Column(modifier = Modifier.clickable { onNewsClicked(news) }) {
        Text(text = news.title)
    }
}
