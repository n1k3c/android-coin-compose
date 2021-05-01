package com.nikec.coincompose.ui.coins

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.ajalt.timberkt.d

@Composable
fun CoinsScreen(
    viewModel: CoinsViewModel
) {
    val state by viewModel.uiState.collectAsState()
    CoinsUI(
        state,
        viewModel::processEvent
    )
}

sealed class CoinsEvent {
    object OnCoinClicked : CoinsEvent()
    object OnShowContentClicked : CoinsEvent()
    object OnShowToastClicked : CoinsEvent()
}