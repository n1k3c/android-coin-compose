package com.nikec.coincompose.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.*
import androidx.navigation.navigation
import com.nikec.coincompose.coins.ui.details.CoinScreen
import com.nikec.coincompose.coins.ui.list.CoinsScreen
import com.nikec.coincompose.core.navigation.DESTINATION_BACK
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.core.navigation.directions.CoinsDirections
import com.nikec.core.ui.theme.CoinComposeTheme
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

                NavHost(navController, startDestination = CoinsDirections.root.destination) {
                    navigation(
                        startDestination = CoinsDirections.coinsList.destination,
                        route = CoinsDirections.root.route
                    ) {
                        composable(CoinsDirections.coinsList.route) {
                            CoinsScreen(viewModel = hiltViewModel())
                        }
                        composable(CoinsDirections.coin().route) {
                            CoinScreen(viewModel = hiltViewModel())
                        }
                    }
                }
            }
        }
    }
}
