package com.nikec.coincompose.coin.ui

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.nikec.coincompose.R

@Composable
fun CoinScreen(viewModel: CoinViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onBackClicked()
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
            )
        },
    ) {

    }
}
