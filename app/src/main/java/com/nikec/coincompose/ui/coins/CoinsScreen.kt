package com.nikec.coincompose.ui.coins

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.nikec.coincompose.utils.CollectSideEffect

@Composable
fun CoinsScreen(
    viewModel: CoinsViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    CollectSideEffect(flow = viewModel.sideEffect) {
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


    CoinsUI(
        state,
        viewModel::processEvent,
    )
}
