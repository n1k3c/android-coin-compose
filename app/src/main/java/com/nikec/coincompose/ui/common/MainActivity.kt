package com.nikec.coincompose.ui.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.*
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.navigation.CoinsDirections
import com.nikec.coincompose.navigation.NavigationManager
import com.nikec.coincompose.ui.coin.CoinScreen
import com.nikec.coincompose.ui.coins.CoinsScreen
import com.nikec.coincompose.ui.common.theme.CoinComposeTheme
import dagger.hilt.android.AndroidEntryPoint
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
                    i { command.destination.toString()}
                    if (command.destination.isNotEmpty()) {
                        navController.navigate(command.destination)
                    }
                }

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