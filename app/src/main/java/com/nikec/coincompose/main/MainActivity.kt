package com.nikec.coincompose.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
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

    sealed class BottomBarItem(
        @StringRes val title: Int,
        val route: String,
        val icon: ImageVector
    ) {
        object Coins : BottomBarItem(
            R.string.coins,
            CoinsDirections.coinsList.route,
            Icons.Default.CheckCircle
        )

        object News :
            BottomBarItem(R.string.news, NewsDirections.newsList.route, Icons.Default.List)
    }

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            CoinComposeTheme {
                val navController = rememberNavController()
                val bottomBarItems = listOf(BottomBarItem.Coins, BottomBarItem.News)
                val enabledRoutes =
                    listOf(CoinsDirections.coinsList.route, NewsDirections.newsList.route)

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
                        BottomNavigationBar(
                            navController = navController,
                            items = bottomBarItems,
                            enabledRoutes = enabledRoutes
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = CoinsDirections.root.destination
                    ) {
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

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun BottomNavigationBar(
        navController: NavHostController,
        items: List<BottomBarItem>,
        enabledRoutes: List<String>
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry.value?.destination
        var visibility by remember { mutableStateOf(true) }

        LaunchedEffect(currentDestination) {
            visibility =
                currentDestination?.hierarchy?.any { enabledRoutes.contains(it.route) } != false
        }

        AnimatedVisibility(
            visible = visibility,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            BottomNavigation(backgroundColor = Color.Black) {
                items.forEach { item ->
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
                                imageVector = item.icon,
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        alwaysShowLabel = false,
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.DarkGray
                    )
                }
            }
        }
    }
}
