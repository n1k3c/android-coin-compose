package com.nikec.coincompose.coins.ui.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.nikec.coincompose.core.model.Coin
import com.nikec.core.ui.atoms.ConnectivityStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun CoinsContent(coinsList: LazyPagingItems<Coin>, onCoinClicked: (Coin) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConnectivityStatus()
        Spacer(modifier = Modifier.height(3.dp))
        CoinsList(coinsList = coinsList, onCoinClicked = onCoinClicked)
    }
}

@Composable
private fun CoinsList(coinsList: LazyPagingItems<Coin>, onCoinClicked: (Coin) -> Unit) {
    LazyColumn {
        items(coinsList) {
            if (it != null) {
                CoinItem(it, onCoinClicked)
            }
        }
    }
}

@Composable
private fun CoinItem(coin: Coin, onCoinClicked: (Coin) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onCoinClicked(coin) }) {
        Text(coin.name, fontSize = 30.sp)
        Spacer(modifier = Modifier.size(60.dp))
    }
}
