package com.nikec.coincompose.settings.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.nikec.coincompose.core.extensions.rememberFlowWithLifecycle

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val currency =
        rememberFlowWithLifecycle(flow = viewModel.currency).collectAsState(initial = null).value

    Scaffold {
        SettingsContent(onCurrencyChange = viewModel::onCurrencyChange, currency = currency)
    }
}
