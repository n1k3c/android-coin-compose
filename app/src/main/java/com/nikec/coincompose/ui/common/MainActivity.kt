package com.nikec.coincompose.ui.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.*
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.navigation.CoinsDirections
import com.nikec.coincompose.navigation.NavigationManager
import com.nikec.coincompose.ui.coin.CoinScreen
import com.nikec.coincompose.ui.coins.CoinsScreen
import com.nikec.coincompose.ui.common.theme.CoinComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoinComposeTheme() {
                val navController = rememberNavController()
                navigationManager.commands.collectAsState().value.also { command ->
                    if (command.destination.isNotEmpty()) {
                        navController.navigate(command.destination)
                    }
                }
//                val lifecycleOwner = LocalLifecycleOwner.current
//                val eventsFlowLifecycleAware = remember(navigationManager.commands, lifecycleOwner) {
//                    navigationManager.commands.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
//                }
//                LaunchedEffect(navigationManager.commands) {
//                    navigationManager.commands.collect {
//                        if (it.destination.isNotEmpty()) {
//                            navController.navigate(it.destination)
//                        }
//                    }
//                }

                NavHost(navController, startDestination = CoinsDirections.root.destination) {
                    navigation(
                        startDestination = CoinsDirections.coins.destination,
                        route = CoinsDirections.root.destination
                    ) {
                        composable(CoinsDirections.coins.destination) {
                            CoinsScreen(
                                navController.hiltNavGraphViewModel(
                                    route = CoinsDirections.coins.destination
                                )
                            )
                        }
                        composable(CoinsDirections.coin.destination) {
                            CoinScreen()
                        }
                    }
                }

//                Surface(color = MaterialTheme.colors.background) {
//                    CoinsScreen()
//                }
            }
        }
    }
}