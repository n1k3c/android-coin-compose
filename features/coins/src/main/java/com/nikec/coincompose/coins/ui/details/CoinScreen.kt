package com.nikec.coincompose.coins.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nikec.coincompose.core.ui.R
import com.nikec.coincompose.data.model.Coin

@Composable
fun CoinScreen(viewModel: CoinViewModel) {
    val viewState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            DetailsTopBar(
                coin = viewState.coin,
                onBackClicked = viewModel::onBackClicked
            )
        },
    ) {
        CoinContent(coin = viewState.coin, currency = viewState.currency)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun DetailsTopBar(coin: Coin?, onBackClicked: () -> Unit) {
    val title = if (coin != null) {
        "${coin.name} (${coin.symbol.uppercase()})"
    } else {
        stringResource(id = R.string.loading)
    }
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(coin?.image),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 6.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        elevation = 1.dp
    )
}
