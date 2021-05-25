package com.nikec.coincompose.ui.coins

import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collect

@Composable
fun CoinsScreen(
    viewModel: CoinsViewModel
) {
    val state by viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    val context = LocalContext.current

    val eventsFlowLifecycleAware = remember(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    LaunchedEffect(eventsFlowLifecycleAware) {
        eventsFlowLifecycleAware.collect {
            when (it) {
                CoinsSideEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        "Some message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    CoinsUI(
        state,
        viewModel::processEvent,
    )
}
