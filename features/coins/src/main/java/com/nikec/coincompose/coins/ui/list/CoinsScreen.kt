package com.nikec.coincompose.coins.ui.list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun CoinsScreen(viewModel: CoinsViewModel) {

    val coins = viewModel.paginatedCoins.collectAsLazyPagingItems()

    Scaffold {
        CoinsContent(coins, viewModel::onCoinClicked)
    }
}
