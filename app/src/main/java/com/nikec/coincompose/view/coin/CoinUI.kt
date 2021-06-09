package com.nikec.coincompose.view.coin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    ) {
        Column() {
            Text("Hello second screen")
            Button(onClick = { event.invoke(CoinEvent.ShowInfoDialog) }) {
                Text(text = "Show info dialog")
            }
        }

        if (viewState.showInfoDialog) {
            AlertDialog(
                onDismissRequest = { event.invoke(CoinEvent.HideInfoDialog) },
                title = { Text("Some Info Title") },
                text = { Text("Some Info Text") },
                buttons = {
                    Column {
                        Divider(
                            Modifier.padding(horizontal = 12.dp),
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                        )
                        TextButton(
                            onClick = { event.invoke(CoinEvent.HideInfoDialog) },
                            shape = RectangleShape,
                            contentPadding = PaddingValues(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("OK")
                        }
                    }
                })
        }
    }
}
