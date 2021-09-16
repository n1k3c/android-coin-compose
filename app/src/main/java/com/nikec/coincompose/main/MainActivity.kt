package com.nikec.coincompose.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navigation
import com.nikec.coincompose.R
import com.nikec.coincompose.coins.navigation.CoinsDirections
import com.nikec.coincompose.coins.ui.details.CoinScreen
import com.nikec.coincompose.coins.ui.list.CoinsScreen
import com.nikec.coincompose.core.navigation.DESTINATION_BACK
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.core.ui.theme.CoinComposeTheme
import com.nikec.coincompose.news.navigation.NewsDirections
import com.nikec.coincompose.news.ui.list.NewsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    enum class BottomBarItem(@StringRes val title: Int, val route: String, val icon: ImageVector) {
        COINS(R.string.coins, CoinsDirections.coinsList.route, Icons.Default.CheckCircle),
        NEWS(R.string.news, NewsDirections.newsList.route, Icons.Default.List)
    }

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            CoinComposeTheme {
                val navController = rememberNavController()
                val bottomBarItems = BottomBarItem.values()

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

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController, items = bottomBarItems)
                    }
                ) {
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
                            composable(NewsDirections.newsList.route) {
                                NewsScreen(viewModel = hiltViewModel())
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomNavigationBar(navController: NavHostController, items: Array<BottomBarItem>) {
        BottomNavigation(backgroundColor = Color.Black) {
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentRoot = backStackEntry.value?.destination?.route
            items.forEach { item ->
                val selected = item.route == currentRoot
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        if (currentRoot != item.route) navController.navigate(item.route)
                    },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                    alwaysShowLabel = false,
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.DarkGray
                )
            }
        }
    }
}
