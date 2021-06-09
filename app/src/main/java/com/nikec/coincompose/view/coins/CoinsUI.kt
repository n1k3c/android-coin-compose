package com.nikec.coincompose.view.coins

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun CoinsUI(
    viewState: CoinsState,
    event: (CoinsEvent) -> Unit,
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

            if (viewState.isLoading) {
                Text("Loading...")
            }

            if (viewState.coinList != null) {
                Text(viewState.coinList.toString())
            }
        }
    })
}
