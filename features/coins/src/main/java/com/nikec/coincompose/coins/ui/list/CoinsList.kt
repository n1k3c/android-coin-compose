package com.nikec.coincompose.coins.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.nikec.coincompose.core.model.Coin

@Composable
fun CoinsList(coinsList: LazyPagingItems<Coin>, onCoinClicked: (Coin) -> Unit) {
    LazyColumn {
        items(coinsList) {
            if (it != null) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCoinClicked(it) }) {
                    Text(it.name, fontSize = 30.sp)
                    Spacer(modifier = Modifier.size(60.dp))
                }
            }
        }
    }
}
