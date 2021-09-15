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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.nikec.coincompose.coins.ui.common.PercentageChangeHeader
import com.nikec.coincompose.coins.ui.common.percentageChangeColorText
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.extensions.formatToString
import com.nikec.coincompose.core.extensions.round
import com.nikec.core.ui.atoms.ConnectivityStatus
import com.nikec.core.ui.atoms.ErrorStatus
import com.nikec.core.ui.theme.*

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
    listState: LazyListState,
    onRefresh: () -> Unit,
    onRetryClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConnectivityStatus()
        Spacer(modifier = Modifier.height(2.dp))
        CoinHeader(scrollState = scrollState)
        CoinsList(
            coinsList = coinsList,
            onCoinClicked = onCoinClicked,
            scrollState = scrollState,
            listState = listState,
            onScrollToTopClicked = onScrollToTopClicked,
            onRefresh = onRefresh,
            onRetryClicked = onRetryClicked
        )
    }
}

@Composable
private fun CoinsList(
    coinsList: LazyPagingItems<Coin>,
    onCoinClicked: (Coin) -> Unit,
    scrollState: ScrollState,
    listState: LazyListState,
    onScrollToTopClicked: () -> Unit,
    onRefresh: () -> Unit,
    onRetryClicked: () -> Unit
) {
    Box {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = coinsList.loadState.refresh is LoadState.Loading),
            onRefresh = { onRefresh() },
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
                        CoinItem(
                            coin = it,
                            onCoinClicked = onCoinClicked,
                            scrollState = scrollState
                        )
                        Divider(color = MaterialTheme.colors.divider)
                    }
                }

                coinsList.apply {
                    if (loadState.append is LoadState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                                    .padding(top = 12.dp),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                    if (loadState.append is LoadState.Error) {
                        item {
                            ErrorStatus(throwable = (loadState.append as LoadState.Error).error) {
                                onRetryClicked()
                            }
                        }
                    }
                }
            }
        }

        if (coinsList.loadState.refresh is LoadState.Error && coinsList.snapshot().isEmpty()) {
            ErrorStatus(throwable = (coinsList.loadState.refresh as LoadState.Error).error) {
                onRetryClicked()
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
            text = stringResource(id = R.string.coin),
            color = MaterialTheme.colors.coinHeaderText,
            modifier = Modifier.width(CellWidthDimensions.NAME.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
        DividerHeader()
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.price),
                modifier = Modifier.width(CellWidthDimensions.PRICE.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.coinHeaderText,
                style = MaterialTheme.typography.body2
            )
            DividerHeader()
            PercentageChangeHeader.values().forEach { percentageChangeValue ->
                PercentageChangeCellHeader(text = stringResource(id = percentageChangeValue.value))
                DividerHeader()
            }
            Text(
                text = stringResource(id = R.string.market_cap),
                modifier = Modifier.width(CellWidthDimensions.MARKET_CAP.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.coinHeaderText,
                style = MaterialTheme.typography.body2
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
                Text(text = coin.symbol.uppercase(), style = MaterialTheme.typography.body2)
            }
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + coin.currentPrice.formatToString(),
                    modifier = Modifier.width(CellWidthDimensions.PRICE.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
                PercentageChangeCell(price = coin.priceChangePercentage1h)
                PercentageChangeCell(price = coin.priceChangePercentage24h)
                PercentageChangeCell(price = coin.priceChangePercentage7d)
                PercentageChangeCell(price = coin.priceChangePercentage30d)
                PercentageChangeCell(price = coin.priceChangePercentage1y)
                Text(
                    text = "$" + coin.marketCap.toDouble().formatToString(),
                    modifier = Modifier.width(CellWidthDimensions.MARKET_CAP.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
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
        style = MaterialTheme.typography.body2
    )
}

@Composable
private fun PercentageChangeCell(price: Double?) {
    val modifier = Modifier.width(CellWidthDimensions.PERCENTAGE_CHANGE.dp)
    if (price == null) {
        Text(
            text = stringResource(id = R.string.not_available),
            modifier = modifier,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
        return
    }
    val color = percentageChangeColorText(price)
    Text(
        text = price.round().toString() + "%",
        modifier = modifier,
        color = color,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.body2,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun DividerHeader() {
    Divider(
        modifier = Modifier
            .background(MaterialTheme.colors.dividerHeader)
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
            backgroundColor = MaterialTheme.colors.fab,
            contentColor = MaterialTheme.colors.fabContent,
            onClick = { onClick.invoke() }) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
        }
    }
}
