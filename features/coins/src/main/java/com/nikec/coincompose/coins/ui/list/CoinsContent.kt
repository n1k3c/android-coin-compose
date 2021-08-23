package com.nikec.coincompose.coins.ui.list

import androidx.compose.foundation.*
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

@Composable
fun CoinsContent(coinsList: LazyPagingItems<Coin>, onCoinClicked: (Coin) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConnectivityStatus()
        Spacer(modifier = Modifier.height(3.dp))
        CoinsList(coinsList = coinsList, onCoinClicked = onCoinClicked)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CoinsList(coinsList: LazyPagingItems<Coin>, onCoinClicked: (Coin) -> Unit) {
    val scrollState = rememberScrollState()
    LazyColumn {
        stickyHeader {
            Row {
                Text("name")
                Row(modifier = Modifier.horizontalScroll(scrollState)) {
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                    Text("price")
                }
            }
        }
        items(coinsList) {
            if (it != null) {
                CoinItem(it, onCoinClicked, scrollState)
            }
        }
    }
}

@Composable
private fun CoinItem(coin: Coin, onCoinClicked: (Coin) -> Unit, scrollState: ScrollState) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onCoinClicked(coin) }) {
        Row {
            Text(coin.name, fontSize = 30.sp)
            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
                Text(coin.current_price.toString(), fontSize = 30.sp)
            }
        }
        Spacer(modifier = Modifier.size(60.dp))
    }
}
