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

package com.nikec.coincompose.coins.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.coins.common.PercentageChangeHeader
import com.nikec.coincompose.coins.common.percentageChangeColorText
import com.nikec.coincompose.core.ui.extensions.formatLocalized
import com.nikec.coincompose.core.ui.extensions.formatToStringWithCurrency
import com.nikec.coincompose.core.ui.extensions.round
import com.nikec.coincompose.core.ui.theme.*
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.model.Currency

@Composable
fun CoinContent(coin: Coin?, currency: Currency) {
    if (coin == null) return

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .padding(bottom = 12.dp)
    ) {
        CoinPrice(
            currentPrice = coin.currentPrice,
            priceChangePercentage1h = coin.priceChangePercentage1h,
            currency = currency
        )
        Sparkline(
            modifier = Modifier
                .height(300.dp)
                .padding(16.dp),
            sparklineIn7d = coin.sparkline,
            currency = currency
        )
        ChangePricePercentages(coin = coin)
        CoinDetailsRow(
            name = stringResource(id = R.string.market_cap),
            value = coin.marketCap.toDouble().formatToStringWithCurrency(currency)
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.market_cap_rank),
            value = "#${coin.marketCapRank}"
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.day_high),
            value = coin.high24h?.formatToStringWithCurrency(currency)
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.day_low),
            value = coin.low24h?.formatToStringWithCurrency(currency)
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.ath),
            value = coin.ath.formatToStringWithCurrency(currency)
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.ath_date),
            value = coin.athDate?.formatLocalized()
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.atl),
            value = coin.atl.formatToStringWithCurrency(currency)
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.atl_date),
            value = coin.atlDate?.formatLocalized()
        )
    }
}

@Composable
private fun CoinPrice(currentPrice: Double?, priceChangePercentage1h: Double?, currency: Currency) {
    Row(
        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currentPrice?.formatToStringWithCurrency(currency)
                ?: stringResource(id = R.string.not_available),
            style = MaterialTheme.typography.body2.copy(fontSize = 26.sp)
        )

        priceChangePercentage1h?.let { price ->
            val color = if (price < 0) Red else Green
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "${price.round()}%",
                color = color,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChangePricePercentages(coin: Coin?) {
    val percentageChangeValues = buildPercentageChangeCellData(coin = coin)
    val dividerHeaderColor = MaterialTheme.colors.dividerHeader
    val dividerCellColor = MaterialTheme.colors.coinHeaderBackground
    val borderStrokeWidth = 6.dp.value

    LazyVerticalGrid(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .height(65.dp),
        cells = GridCells.Fixed(percentageChangeValues.size),
    ) {
        itemsIndexed(percentageChangeValues.keys.toList()) { index, item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(color = MaterialTheme.colors.coinHeaderBackground)
                    .drawBehind {
                        if (index != percentageChangeValues.size - 1) {
                            drawLine(
                                color = dividerHeaderColor,
                                start = Offset(size.width - borderStrokeWidth, 0f),
                                end = Offset(size.width - borderStrokeWidth, size.height),
                                strokeWidth = borderStrokeWidth
                            )
                        }
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = item.value),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.coinHeaderText,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        itemsIndexed(percentageChangeValues.values.toList()) { index, price ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .drawBehind {
                        if (index != percentageChangeValues.size - 1) {
                            // draw right border
                            drawLine(
                                color = dividerCellColor,
                                start = Offset(size.width - borderStrokeWidth, 0f),
                                end = Offset(size.width - borderStrokeWidth, size.height),
                                strokeWidth = borderStrokeWidth
                            )
                        }
                        // draw bottom border
                        drawLine(
                            color = dividerCellColor,
                            start = Offset(0f, size.height - (borderStrokeWidth / 2)),
                            end = Offset(size.width, size.height - (borderStrokeWidth / 2)),
                            strokeWidth = borderStrokeWidth
                        )
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (price != null) {
                        "${price.round()}%"
                    } else {
                        stringResource(id = R.string.not_available)
                    },
                    textAlign = TextAlign.Center,
                    color = if (price != null) {
                        percentageChangeColorText(price)
                    } else {
                        MaterialTheme.colors.coinHeaderText
                    },
                    style = MaterialTheme.typography.body2.copy(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun CoinDetailsRow(name: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = value ?: stringResource(id = R.string.not_available),
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun DetailsDivider() {
    Divider(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        color = MaterialTheme.colors.divider
    )
}

private fun buildPercentageChangeCellData(coin: Coin?): Map<PercentageChangeHeader, Double?> =
    mapOf(
        PercentageChangeHeader.ONE_HOUR to coin?.priceChangePercentage1h,
        PercentageChangeHeader.TWENTY_FOUR_HOURS to coin?.priceChangePercentage24h,
        PercentageChangeHeader.SEVEN_DAYS to coin?.priceChangePercentage7d,
        PercentageChangeHeader.THIRTY_DAYS to coin?.priceChangePercentage30d,
        PercentageChangeHeader.ONE_YEAR to coin?.priceChangePercentage1y
    )
