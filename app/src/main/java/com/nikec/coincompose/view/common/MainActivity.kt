package com.nikec.coincompose.view.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.*
import androidx.navigation.navigation
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.DESTINATION_BACK
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.view.coin.CoinScreen
import com.nikec.coincompose.view.coins.CoinsScreen
import com.nikec.coincompose.view.common.theme.CoinComposeTheme
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
                        startDestination = CoinsDirections.coins.destination,
                        route = CoinsDirections.root.destination
                    ) {
                        composable(CoinsDirections.coins.destination) {
                            CoinsScreen(viewModel = hiltViewModel())
                        }
                        composable(CoinsDirections.coin.destination) {
                            CoinScreen(viewModel = hiltViewModel())
                        }
                    }
                }
            }
        }
    }
}
