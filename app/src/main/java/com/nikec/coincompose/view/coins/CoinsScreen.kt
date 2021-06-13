package com.nikec.coincompose.view.coins

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.github.ajalt.timberkt.d
import com.nikec.coincompose.core.utils.CollectSideEffect

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
