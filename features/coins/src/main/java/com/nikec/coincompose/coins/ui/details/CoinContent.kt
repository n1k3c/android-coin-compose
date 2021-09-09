package com.nikec.coincompose.coins.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nikec.coincompose.coins.R
import com.nikec.coincompose.core.model.Coin
import com.nikec.core.ui.theme.divider
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun CoinContent(coin: Coin?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Sparkline(
            sparklineIn7d = coin?.sparkline
        )
        CoinDetailsRow(value = "$" + coin?.ath)
        DetailsDivider()
        CoinDetailsRow(
            value = coin?.athDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
                ?: stringResource(R.string.not_available)
        )
    }
}

@Composable
private fun CoinDetailsRow(value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            stringResource(R.string.ath),
            style = MaterialTheme.typography.body2
        )
        Text(
            value,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun DetailsDivider() {
    Divider(color = MaterialTheme.colors.divider)
}
