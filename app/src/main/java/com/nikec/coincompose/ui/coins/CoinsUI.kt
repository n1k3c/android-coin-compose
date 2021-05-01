package com.nikec.coincompose.ui.coins

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.github.ajalt.timberkt.d

@Composable
fun CoinsUI(
    viewState: CoinsState,
    events: (event: CoinsEvent) -> Unit
) {
    val context = LocalContext.current
    Scaffold(content = {
        Column() {
            Text("Hello first screen!")
            Button(onClick = {
                events.invoke(CoinsEvent.OnCoinClicked)
            }) {
                Text("Navigate")
            }
            Button(onClick = {
                events.invoke(CoinsEvent.OnShowContentClicked)
            }) {
                Text("Show content")
            }
            Button(onClick = {
                events.invoke(CoinsEvent.OnShowToastClicked)
            }) {
                Text("Show toast")
            }

            when (viewState) {
                is CoinsState.CoinsContent -> {
                    d { "COINS-CONTENT" }
                    Text(viewState.coinList.toString())
                }
                is CoinsState.Error -> TODO()
                CoinsState.Idle -> {
                    d { "IDLE" }
                    Text("idle")
                }
                CoinsState.Loading -> TODO()
                CoinsState.ShowToast -> {
                    Toast.makeText(
                        context,
                        "Some message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }
    })
}