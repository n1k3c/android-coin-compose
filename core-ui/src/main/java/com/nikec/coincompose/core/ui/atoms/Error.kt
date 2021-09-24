package com.nikec.coincompose.core.ui.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikec.coincompose.core.ui.R
import com.nikec.coincompose.core.utils.NoInternetThrowable

@Composable
fun ErrorStatus(throwable: Throwable, onClick: () -> Unit) {
    val message = when (throwable) {
        is NoInternetThrowable -> {
            stringResource(id = R.string.no_internet_connection)
        }
        else -> stringResource(id = R.string.something_went_wrong)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = message, style = MaterialTheme.typography.body2)
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            onClick = { onClick() }
        ) {
            Text(text = stringResource(id = R.string.retry), style = MaterialTheme.typography.body2)
        }
    }
}
