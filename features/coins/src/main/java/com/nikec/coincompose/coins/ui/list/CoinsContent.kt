package com.nikec.coincompose.coins.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.round
import com.nikec.core.ui.atoms.ConnectivityStatus

@Composable
fun CoinsContent(
    coinsList: LazyPagingItems<Coin>,
    onCoinClicked: (Coin) -> Unit
) {
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
                RowItem(text = stringResource(R.string.coin))
                Row(modifier = Modifier.horizontalScroll(scrollState)) {
                    RowItem(text = stringResource(R.string.price))
                    RowItem(text = stringResource(R.string.one_hour))
                    RowItem(text = stringResource(R.string.twenty_four_hours))
                    RowItem(text = stringResource(R.string.seven_days))
                    RowItem(text = stringResource(R.string.thirty_days))
                    RowItem(text = stringResource(R.string.market_cap))
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
            RowItem(text = coin.symbol)
            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                RowItem(text = coin.currentPrice.toString())
                RowItem(text = coin.priceChangePercentage1h?.round().toString())
                RowItem(text = coin.priceChangePercentage24h?.round().toString())
                RowItem(text = coin.priceChangePercentage7d?.round().toString())
                RowItem(text = coin.priceChangePercentage30d?.round().toString())
                RowItem(text = coin.marketCap.toString())
            }
        }
    }
}


@Composable
private fun RowItem(text: String) {
    Text(text = text, modifier = Modifier.width(80.dp))
}
