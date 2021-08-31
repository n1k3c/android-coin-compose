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
    val coinsList = viewModel.paginatedCoins.collectAsLazyPagingItems()
    val coinListAction = rememberFlowWithLifecycle(viewModel.coinListAction)
    val scrollState = rememberScrollState()
    val listState = rememberLazyListState()

    LaunchedEffect(coinListAction) {
        coinListAction.collect {
            when (it) {
                CoinListAction.Refresh -> coinsList.refresh()
                CoinListAction.Retry -> coinsList.retry()
                CoinListAction.ScrollToTop -> listState.scrollToItem(0)
            }
        }
    }

    Scaffold {
        CoinsContent(
            coinsList = coinsList,
            onCoinClicked = viewModel::onCoinClicked,
            onScrollToTopClicked = viewModel::onScrollToTopClicked,
            onRefresh = viewModel::onRefresh,
            onRetryClicked = viewModel::onRetryClicked,
            scrollState = scrollState,
            listState = listState
        )
    }
}
