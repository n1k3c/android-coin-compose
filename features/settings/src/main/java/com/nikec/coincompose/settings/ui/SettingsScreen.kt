package com.nikec.coincompose.settings.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {

    Scaffold {
        SettingsContent()
    }
}
