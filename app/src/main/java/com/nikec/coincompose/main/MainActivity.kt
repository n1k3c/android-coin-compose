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

package com.nikec.coincompose.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.*
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.DESTINATION_BACK
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.core.ui.theme.CoinComposeTheme
import com.nikec.coincompose.navigation.BottomNavigationBar
import com.nikec.coincompose.navigation.coinsGraph
import com.nikec.coincompose.navigation.newsGraph
import com.nikec.coincompose.navigation.settingsGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            CoinComposeTheme {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()

                val lifecycleOwner = LocalLifecycleOwner.current
                val eventsFlowLifecycleAware =
                    remember(navigationManager.commandFlow, lifecycleOwner) {
                        navigationManager.commandFlow.flowWithLifecycle(
                            lifecycleOwner.lifecycle,
                            Lifecycle.State.STARTED
                        )
                    }

                LaunchedEffect(eventsFlowLifecycleAware) {
                    eventsFlowLifecycleAware.collect {
                        if (it.destination == DESTINATION_BACK) {
                            navController.navigateUp()
                            return@collect
                        }

                        if (it.destination.isNotEmpty()) {
                            navController.navigate(it.destination)
                        }
                    }
                }

                SetupSystemUi(systemUiController = systemUiController)

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = CoinsDirections.root.destination
                    ) {
                        coinsGraph()
                        newsGraph()
                        settingsGraph()
                    }
                }
            }
        }
    }

    @Composable
    private fun SetupSystemUi(systemUiController: SystemUiController) {
        val barsColor = MaterialTheme.colors.primaryVariant

        SideEffect {
            systemUiController.setStatusBarColor(color = barsColor)
            systemUiController.setNavigationBarColor(color = barsColor)
        }
    }
}
