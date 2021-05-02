package com.nikec.coincompose.ui.coins

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle

@Composable
fun CoinsScreen(
    viewModel: CoinsViewModel
) {
    val state by viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    val eventsFlowLifecycleAware = remember(viewModel.eventsFlow, lifecycleOwner) {
        viewModel.eventsFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val event by eventsFlowLifecycleAware.collectAsState(null)

    CoinsUI(
        state,
        viewModel::processEvent,
        event
    )
}

sealed class CoinsEvent {
    object OnCoinClicked : CoinsEvent()
    object OnShowContentClicked : CoinsEvent()
    object OnShowContentClicked2 : CoinsEvent()
    object OnShowToastClicked : CoinsEvent()
}