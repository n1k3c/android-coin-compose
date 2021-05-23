package com.nikec.coincompose.ui.coins

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.github.ajalt.timberkt.d

@Composable
fun CoinsUI(
    viewState: CoinsState,
    event: (event: CoinsEvent) -> Unit,
) {
    Scaffold(content = {
        Column() {
            Text("Hello first screen!")
            Button(onClick = {
                event.invoke(CoinsEvent.OnCoinClicked)
            }) {
                Text("Navigate")
            }
            Button(onClick = {
                event.invoke(CoinsEvent.OnShowContentClicked)
            }) {
                Text("Show content")
            }
            Button(onClick = {
                event.invoke(CoinsEvent.OnShowContentClicked2)
            }) {
                Text("Show content2 ")
            }
            Button(onClick = {
                event.invoke(CoinsEvent.OnShowToastClicked)
            }) {
                Text("Show toast")
            }

            when (viewState) {
                is CoinsState.CoinsContent -> Text(viewState.coinList.toString())
                CoinsState.Idle -> Text("Idle...")
                CoinsState.Loading -> Text("Loading...")
            }
        }
    })
}
