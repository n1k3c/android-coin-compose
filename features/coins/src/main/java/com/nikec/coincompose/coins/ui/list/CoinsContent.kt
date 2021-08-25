package com.nikec.coincompose.coins.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.round
import com.nikec.core.ui.atoms.ConnectivityStatus
import com.nikec.core.ui.theme.Green
import com.nikec.core.ui.theme.Red
import java.text.NumberFormat
import java.util.*

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
                Text(
                    text = stringResource(R.string.coin),
                    modifier = Modifier.width(CellWidthDimensions.NAME.dp)
                )
                Row(modifier = Modifier.horizontalScroll(scrollState)) {
                    Text(
                        text = stringResource(R.string.price),
                        modifier = Modifier.width(CellWidthDimensions.PRICE.dp)
                    )
                    PercentageChangeCellHeader(text = stringResource(R.string.one_hour))
                    PercentageChangeCellHeader(text = stringResource(R.string.twenty_four_hours))
                    PercentageChangeCellHeader(text = stringResource(R.string.seven_days))
                    PercentageChangeCellHeader(text = stringResource(R.string.thirty_days))
                    Text(
                        text = stringResource(R.string.market_cap),
                        modifier = Modifier.width(CellWidthDimensions.MARKET_CAP.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
        items(coinsList) {
            if (it != null) {
                CoinItem(it, onCoinClicked, scrollState)
            }
        }
    }
}

private enum class CellWidthDimensions(val dp: Dp) {
    NAME(80.dp),
    PRICE(100.dp),
    PERCENTAGE_CHANGE(80.dp),
    MARKET_CAP(165.dp)
}

@Composable
private fun CoinItem(coin: Coin, onCoinClicked: (Coin) -> Unit, scrollState: ScrollState) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onCoinClicked(coin) }) {
        Row {
            Text(text = coin.symbol, modifier = Modifier.width(CellWidthDimensions.NAME.dp))
            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                Text(
                    text = "$" + NumberFormat.getInstance(Locale.getDefault())
                        .format(coin.currentPrice),
                    Modifier.width(CellWidthDimensions.PRICE.dp)
                )
                PercentageChangeCell(price = coin.priceChangePercentage1h)
                PercentageChangeCell(price = coin.priceChangePercentage24h)
                PercentageChangeCell(price = coin.priceChangePercentage7d)
                PercentageChangeCell(price = coin.priceChangePercentage30d)
                Text(
                    text = "$" + NumberFormat.getInstance(Locale.getDefault())
                        .format(coin.marketCap),
                    Modifier.width(CellWidthDimensions.MARKET_CAP.dp)
                )
            }
        }
    }
}

@Composable
private fun PercentageChangeCellHeader(text: String) {
    Text(text = text, modifier = Modifier.width(CellWidthDimensions.PERCENTAGE_CHANGE.dp))
}

@Composable
private fun PercentageChangeCell(price: Double?) {
    val modifier = Modifier.width(CellWidthDimensions.PERCENTAGE_CHANGE.dp)
    if (price == null) {
        Text(
            text = stringResource(R.string.not_available),
            modifier = modifier
        )
        return
    }
    val color = if (price < 0) Red else Green
    Text(
        text = price.round().toString(),
        modifier = modifier,
        color = color
    )
}
