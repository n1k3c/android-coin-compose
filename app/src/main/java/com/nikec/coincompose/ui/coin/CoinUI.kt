package com.nikec.coincompose.ui.coin

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nikec.coincompose.R

@Composable
fun CoinUI(
    viewState: CoinState,
    event: (CoinEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            event.invoke(CoinEvent.OnBackClicked)
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
            )
        },
        content = {
            Column() {
                Text("Hello second screen")
            }
        }
    )
}
