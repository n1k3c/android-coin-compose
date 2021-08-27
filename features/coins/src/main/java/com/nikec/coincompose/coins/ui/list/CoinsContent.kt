package com.nikec.coincompose.coins.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.round
import com.nikec.core.ui.atoms.ConnectivityStatus
import com.nikec.core.ui.theme.Green
import com.nikec.core.ui.theme.Red
import com.nikec.core.ui.theme.coinHeaderBackground
import com.nikec.core.ui.theme.coinHeaderText
import java.text.NumberFormat
import java.util.*

private enum class CellWidthDimensions(val dp: Dp) {
    NAME(65.dp),
    PRICE(100.dp),
    PERCENTAGE_CHANGE(100.dp),
    MARKET_CAP(175.dp)
}

@Composable
fun CoinsContent(
    coinsList: LazyPagingItems<Coin>,
    onCoinClicked: (Coin) -> Unit,
    onScrollToTopClicked: () -> Unit,
    scrollState: ScrollState,
    listState: LazyListState
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConnectivityStatus()
        Spacer(modifier = Modifier.height(2.dp))
        CoinHeader(scrollState)
        CoinsList(
            coinsList = coinsList,
            onCoinClicked = onCoinClicked,
            scrollState = scrollState,
            listState = listState,
            onScrollToTopClicked = onScrollToTopClicked
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CoinsList(
    coinsList: LazyPagingItems<Coin>,
    onCoinClicked: (Coin) -> Unit,
    scrollState: ScrollState,
    listState: LazyListState,
    onScrollToTopClicked: () -> Unit
) {
    Box {
        SwipeRefresh(
            state = rememberSwipeRefreshState(coinsList.loadState.refresh is LoadState.Loading),
            onRefresh = { coinsList.refresh() },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    contentColor = MaterialTheme.colors.secondary,
                )
            }) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(coinsList) {
                    if (it != null) {
                        CoinItem(it, onCoinClicked, scrollState)
                        Divider(color = MaterialTheme.colors.coinHeaderBackground)
                    }
                }
            }
        }
        ScrollToTopButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            listState = listState,
            onClick = { onScrollToTopClicked() }
        )
    }
}

@Composable
private fun CoinHeader(scrollState: ScrollState) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.coinHeaderBackground)
            .height(24.dp)
    ) {
        Text(
            text = stringResource(R.string.coin),
            color = MaterialTheme.colors.coinHeaderText,
            modifier = Modifier.width(CellWidthDimensions.NAME.dp),
            textAlign = TextAlign.Center
        )
        DividerHeader()
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            Text(
                text = stringResource(R.string.price),
                modifier = Modifier.width(CellWidthDimensions.PRICE.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.coinHeaderText,
            )
            DividerHeader()
            PercentageChangeCellHeader(text = stringResource(R.string.one_hour))
            DividerHeader()
            PercentageChangeCellHeader(text = stringResource(R.string.twenty_four_hours))
            DividerHeader()
            PercentageChangeCellHeader(text = stringResource(R.string.seven_days))
            DividerHeader()
            PercentageChangeCellHeader(text = stringResource(R.string.thirty_days))
            DividerHeader()
            PercentageChangeCellHeader(text = stringResource(R.string.one_year))
            DividerHeader()
            Text(
                text = stringResource(R.string.market_cap),
                modifier = Modifier.width(CellWidthDimensions.MARKET_CAP.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun CoinItem(coin: Coin, onCoinClicked: (Coin) -> Unit, scrollState: ScrollState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCoinClicked(coin) }
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Row(modifier = Modifier.height(40.dp)) {
            Column(
                modifier = Modifier.width(CellWidthDimensions.NAME.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(coin.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(bottom = 4.dp)
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
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.coinHeaderText,
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
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.coinHeaderText,
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

@Composable
private fun DividerHeader() {
    Divider(
        modifier = Modifier
            .background(Color.White)
            .width(1.dp)
            .fillMaxHeight()
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ScrollToTopButton(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    onClick: () -> Unit
) {
    val visibility = listState.firstVisibleItemIndex > 0

    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.secondary,
            onClick = { onClick.invoke() }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
        }
    }
}
