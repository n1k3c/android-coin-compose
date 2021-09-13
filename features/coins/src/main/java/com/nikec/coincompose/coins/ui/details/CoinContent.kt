package com.nikec.coincompose.coins.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.formatToString
import com.nikec.core.ui.theme.divider
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun CoinContent(coin: Coin?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Sparkline(
            modifier = Modifier
                .height(300.dp)
                .padding(16.dp),
            sparklineIn7d = coin?.sparkline
        )
        CoinDetailsRow(
            name = stringResource(id = R.string.market_cap),
            value = "$${coin?.marketCap?.toDouble()?.formatToString()}"
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.market_cap_rank),
            value = if (coin?.marketCapRank != null) "#${coin.marketCapRank}" else null
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.day_high),
            value = if (coin?.high24h != null) "$${coin.high24h?.formatToString()}" else null
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.day_low),
            value = if (coin?.low24h != null) "$${coin.low24h?.formatToString()}" else null
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.ath),
            value = if (coin?.ath != null) "$${coin.ath.formatToString()}" else null
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.ath_date),
            value = coin?.athDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.atl),
            value = if (coin?.atl != null) "$${coin.atl.formatToString()}" else null
        )
        DetailsDivider()
        CoinDetailsRow(
            name = stringResource(id = R.string.atl_date),
            value = coin?.atlDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        )
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
            name,
            style = MaterialTheme.typography.body2
        )
        Text(
            value ?: stringResource(id = R.string.not_available),
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
