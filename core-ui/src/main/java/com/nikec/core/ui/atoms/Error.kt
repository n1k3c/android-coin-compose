package com.nikec.core.ui.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nikec.coincompose.core.utils.NoInternetThrowable
import com.nikec.core.ui.R

@Composable
fun ErrorStatus(throwable: Throwable, onClick: () -> Unit) {
    val message = when (throwable) {
        is NoInternetThrowable -> {
            stringResource(R.string.no_internet_connection)
        }
        else -> stringResource(R.string.something_went_wrong)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = message)
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            onClick = { onClick() }
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}
