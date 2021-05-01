package com.nikec.coincompose.ui.coin

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.nikec.coincompose.ui.common.theme.CoinComposeTheme

@Composable
fun CoinScreen() {
    CoinComposeTheme() {
        Scaffold(
            content = {
                Column() {
                    Text("Hello second screen")
                }
            }
        )
    }

}