/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nikec.coincompose.coins.list

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
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.coins.common.PercentageChangeHeader
import com.nikec.coincompose.coins.common.percentageChangeColorText
import com.nikec.coincompose.core.ui.atoms.AppendLoadingIndicator
import com.nikec.coincompose.core.ui.atoms.ConnectivityStatus
import com.nikec.coincompose.core.ui.atoms.ErrorStatus
import com.nikec.coincompose.core.ui.atoms.SwipeToRefreshIndicator
import com.nikec.coincompose.core.ui.extensions.formatToStringWithCurrency
import com.nikec.coincompose.core.ui.extensions.round
import com.nikec.coincompose.core.ui.theme.*
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.model.Currency

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
    onRetryClicked: () -> Unit,
    currency: Currency?
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
            onRetryClicked = onRetryClicked,
            currency = currency
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
    onRetryClicked: () -> Unit,
    currency: Currency?
) {
    Box {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(isRefreshing = coinsList.loadState.refresh is LoadState.Loading),
            onRefresh = { onRefresh() },
            indicator = { state, trigger ->
                SwipeToRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger
                )
            }) {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(coinsList) { coin ->
                    coin?.let {
                        CoinItem(
                            coin = coin,
                            onCoinClicked = onCoinClicked,
                            scrollState = scrollState,
                            currency = currency
                        )
                        Divider(color = MaterialTheme.colors.divider)
                    }
                }

                coinsList.apply {
                    if (loadState.append is LoadState.Loading) {
                        item {
                            AppendLoadingIndicator()
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
private fun CoinItem(
    coin: Coin,
    onCoinClicked: (Coin) -> Unit,
    scrollState: ScrollState,
    currency: Currency?
) {
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
                    modifier = Modifier
                        .size(20.dp)
                        .padding(bottom = 4.dp),
                    painter = rememberImagePainter(
                        data = coin.image,
                        builder = { crossfade(true) }),
                    contentDescription = "${coin.name} image"
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
                    text = coin.currentPrice.formatToStringWithCurrency(currency),
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
                    text = coin.marketCap.toDouble().formatToStringWithCurrency(currency),
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
