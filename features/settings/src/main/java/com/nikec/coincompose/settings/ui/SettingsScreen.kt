package com.nikec.coincompose.settings.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val currency by viewModel.currency.collectAsState()

    Scaffold {
        SettingsContent(onCurrencyChange = viewModel::onCurrencyChange, currency = currency)
    }
}
