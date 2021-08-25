package com.nikec.coincompose.coins.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.round
import com.nikec.core.ui.atoms.ConnectivityStatus
import com.nikec.core.ui.theme.Green
import com.nikec.core.ui.theme.Red
import java.text.NumberFormat
import java.util.*

private enum class CellWidthDimensions(val dp: Dp) {
    NAME(65.dp),
    PRICE(100.dp),
    PERCENTAGE_CHANGE(100.dp),
    MARKET_CAP(165.dp)
}

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
    SwipeRefresh(
        state = rememberSwipeRefreshState(coinsList.loadState.refresh is LoadState.Loading),
        onRefresh = { coinsList.refresh() }) {
        LazyColumn {
            stickyHeader {
                Row {
                    Text(
                        text = stringResource(R.string.coin),
                        modifier = Modifier.width(CellWidthDimensions.NAME.dp),
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.horizontalScroll(scrollState)
                    ) {
                        Text(
                            text = stringResource(R.string.price),
                            modifier = Modifier.width(CellWidthDimensions.PRICE.dp),
                            textAlign = TextAlign.Center
                        )
                        PercentageChangeCellHeader(text = stringResource(R.string.one_hour))
                        PercentageChangeCellHeader(text = stringResource(R.string.twenty_four_hours))
                        PercentageChangeCellHeader(text = stringResource(R.string.seven_days))
                        PercentageChangeCellHeader(text = stringResource(R.string.thirty_days))
                        PercentageChangeCellHeader(text = stringResource(R.string.one_year))
                        Text(
                            text = stringResource(R.string.market_cap),
                            modifier = Modifier.width(CellWidthDimensions.MARKET_CAP.dp),
                            textAlign = TextAlign.Center
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
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun CoinItem(coin: Coin, onCoinClicked: (Coin) -> Unit, scrollState: ScrollState) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onCoinClicked(coin) }
        .padding(top = 8.dp)) {
        Row(modifier = Modifier.height(40.dp)) {
            Column(
                modifier = Modifier.width(CellWidthDimensions.NAME.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(coin.image),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(text = coin.symbol.uppercase())
            }
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + NumberFormat.getInstance(Locale.getDefault())
                        .format(coin.currentPrice),
                    modifier = Modifier.width(CellWidthDimensions.PRICE.dp),
                    textAlign = TextAlign.Center
                )
                PercentageChangeCell(price = coin.priceChangePercentage1h)
                PercentageChangeCell(price = coin.priceChangePercentage24h)
                PercentageChangeCell(price = coin.priceChangePercentage7d)
                PercentageChangeCell(price = coin.priceChangePercentage30d)
                PercentageChangeCell(price = coin.priceChangePercentage1y)
                Text(
                    text = "$" + NumberFormat.getInstance(Locale.getDefault())
                        .format(coin.marketCap),
                    modifier = Modifier.width(CellWidthDimensions.MARKET_CAP.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun PercentageChangeCellHeader(text: String) {
    Text(
        text = text,
        modifier = Modifier.width(CellWidthDimensions.PERCENTAGE_CHANGE.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun PercentageChangeCell(price: Double?) {
    val modifier = Modifier.width(CellWidthDimensions.PERCENTAGE_CHANGE.dp)
    if (price == null) {
        Text(
            text = stringResource(R.string.not_available),
            modifier = modifier,
            textAlign = TextAlign.Center
        )
        return
    }
    val color = if (price < 0) Red else Green
    Text(
        text = price.round().toString() + "%",
        modifier = modifier,
        color = color,
        textAlign = TextAlign.Center
    )
}
