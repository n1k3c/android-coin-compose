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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nikec.coincompose.R
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.NewsDirections
import com.nikec.coincompose.core.navigation.SettingsDirections

private sealed class NavigationBarItem(
    @StringRes val title: Int,
    val route: String,
    @DrawableRes val icon: Int
) {
    object Coins : NavigationBarItem(
        R.string.coins,
        CoinsDirections.coinsList.route,
        R.drawable.ic_coins
    )

    object News :
        NavigationBarItem(R.string.news, NewsDirections.newsList.route, R.drawable.ic_news)

    object Settings : NavigationBarItem(
        R.string.settings,
        SettingsDirections.settings.route,
        R.drawable.ic_settings
    )
}

private val navigationBarItems =
    listOf(NavigationBarItem.Coins, NavigationBarItem.News, NavigationBarItem.Settings)

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun BottomNavigationBar(
    navController: NavHostController,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination
    var visibility by remember { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        visibility =
            currentDestination?.hierarchy?.any { item ->
                navigationBarItems.map { it.route }.contains(item.route)
            } != false
    }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        BottomNavigation(elevation = 1.dp) {
            navigationBarItems.forEach { item ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == item.route } == true
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title)
                        )
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}
