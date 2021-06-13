package com.nikec.coincompose.view.coins

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.github.ajalt.timberkt.d

@OptIn(ExperimentalUnitApi::class)
@Composable
fun CoinsUI(
    viewState: CoinsState,
    event: (CoinsEvent) -> Unit,
) {
    val coins = viewState.coinListPaginated.collectAsLazyPagingItems()
    Scaffold(content = {
        LazyColumn {
            items(coins) {
                if (it != null) {
                    Text(it.name, fontSize = TextUnit(30f, TextUnitType.Sp))
                    Spacer(modifier = Modifier.size(60.dp))
                }
            }
        }
    })
}
