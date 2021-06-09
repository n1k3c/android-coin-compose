package com.nikec.coincompose.view.coin

import androidx.compose.runtime.*

@Composable
fun CoinScreen(
    viewModel: CoinViewModel
) {
    val viewState by viewModel.uiState.collectAsState()

    CoinUI(
        viewState,
        viewModel::processEvent
    )
}
