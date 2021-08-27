package com.nikec.coincompose.coins.ui.list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.compose.collectAsLazyPagingItems
import com.nikec.coincompose.core.utils.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect

@Composable
fun CoinsScreen(viewModel: CoinsViewModel) {
    val coins = viewModel.paginatedCoins.collectAsLazyPagingItems()
    val scrollToTop = rememberFlowWithLifecycle(viewModel.scrollToTopFlow)
    val scrollState = rememberScrollState()
    val listState = rememberLazyListState()

    LaunchedEffect(scrollToTop) {
        scrollToTop.collect {
            listState.scrollToItem(0)
        }
    }

    Scaffold {
        CoinsContent(
            coinsList = coins,
            onCoinClicked = viewModel::onCoinClicked,
            onScrollToTopClicked = viewModel::onScrollToTopClicked,
            scrollState = scrollState,
            listState = listState
        )
    }
}
