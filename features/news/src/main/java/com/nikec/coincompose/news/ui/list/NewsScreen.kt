package com.nikec.coincompose.news.ui.list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val coinsList = viewModel.paginatedNews.collectAsLazyPagingItems()

    Scaffold {
        NewsContent(coinsList = coinsList)
    }
}
