package com.nikec.coincompose.coins.ui.list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nikec.coincompose.core.ui.extensions.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect

@Composable
fun CoinsScreen(viewModel: CoinsViewModel) {
    val coinsList = viewModel.paginatedCoins.collectAsLazyPagingItems()
    val coinListEvents = rememberFlowWithLifecycle(flow = viewModel.coinListEvents)
    val currency =
        rememberFlowWithLifecycle(flow = viewModel.currency).collectAsState(initial = null).value
    val scrollState = rememberScrollState()
    val listState = rememberLazyListState()

    LaunchedEffect(coinListEvents) {
        coinListEvents.collect {
            when (it) {
                CoinListEvent.Refresh -> coinsList.refresh()
                CoinListEvent.Retry -> coinsList.retry()
                CoinListEvent.ScrollToTop -> listState.animateScrollToItem(0)
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
            currency = currency,
            scrollState = scrollState,
            listState = listState
        )
    }
}
