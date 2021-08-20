package com.nikec.coincompose.coins.ui.details

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.nikec.core.ui.atoms.ToolbarWithBack

@Composable
fun CoinScreen(viewModel: CoinViewModel) {
    Scaffold(
        topBar = {
            ToolbarWithBack("Details") {
                viewModel.onBackClicked()
            }
        },
    ) {

    }
}
