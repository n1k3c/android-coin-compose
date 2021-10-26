/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nikec.coincompose.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nikec.coincompose.coins.details.CoinScreen
import com.nikec.coincompose.coins.list.CoinsScreen
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.NewsDirections
import com.nikec.coincompose.core.navigation.SettingsDirections
import com.nikec.coincompose.news.list.NewsScreen
import com.nikec.coincompose.settings.SettingsScreen

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
