package com.nikec.coincompose.coins.ui.details

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nikec.coincompose.coins.R

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
