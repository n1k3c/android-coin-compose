package com.nikec.coincompose.ui.coins

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.github.ajalt.timberkt.d

@Composable
fun CoinsUI(
    viewState: CoinsState?,
    events: (event: CoinsEvent) -> Unit,
    event: Event?
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

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
                events.invoke(CoinsEvent.OnShowContentClicked2)
            }) {
                Text("Show content2 ")
            }
            Button(onClick = {
                events.invoke(CoinsEvent.OnShowToastClicked)
            }) {
                Text("Show toast")
            }

            if (viewState?.isLoading == true) {
                Text("Loading...")
            }

            if (viewState?.isIdle == true) {
                Text("Idle...")
            }

            if (viewState?.coinContent?.isNotEmpty() == true) {
                d { "show content" }
                Text(viewState.coinContent.toString())
            }

            when (event) {
                Event.ShowToast -> {
                        d { "Show toast" }
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