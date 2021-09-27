package com.nikec.coincompose.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikec.coincompose.coins.navigation.CoinsDirections
import com.nikec.coincompose.coins.ui.details.CoinScreen
import com.nikec.coincompose.coins.ui.list.CoinsScreen
import com.nikec.coincompose.news.navigation.NewsDirections
import com.nikec.coincompose.news.ui.list.NewsScreen
import com.nikec.coincompose.settings.navigation.SettingsDirections
import com.nikec.coincompose.settings.ui.SettingsScreen

internal fun NavGraphBuilder.coinsGraph() {
    navigation(
        startDestination = CoinsDirections.coinsList.destination,
        route = CoinsDirections.root.route
    ) {
        composable(route = CoinsDirections.coinsList.route) {
            CoinsScreen(viewModel = hiltViewModel())
        }
        composable(
            route = CoinsDirections.coinDetails().route,
            arguments = CoinsDirections.coinDetails().arguments
        ) {
            CoinScreen(viewModel = hiltViewModel())
        }
    }
}

internal fun NavGraphBuilder.newsGraph() {
    composable(route = NewsDirections.newsList.route) {
        NewsScreen(viewModel = hiltViewModel())
    }
}

internal fun NavGraphBuilder.settingsGraph() {
    composable(route = SettingsDirections.settings.route) {
        SettingsScreen(viewModel = hiltViewModel())
    }
}
