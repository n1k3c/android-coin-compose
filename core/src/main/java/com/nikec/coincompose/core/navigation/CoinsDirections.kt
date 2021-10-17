package com.nikec.coincompose.core.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

object CoinsDirections {

    const val COIN_ID = "coinId"

    val root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coins"

        override val route = destination
    }

    val coinsList = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coinsList"

        override val route = destination
    }

    fun coinDetails(coinId: String? = null): NavigationCommand {
        return object : NavigationCommand {

            override val arguments = listOf(
                navArgument(COIN_ID) { type = NavType.StringType }
            )

            override val destination = "coin/$coinId"

            override val route = "coin/{$COIN_ID}"
        }
    }
}
