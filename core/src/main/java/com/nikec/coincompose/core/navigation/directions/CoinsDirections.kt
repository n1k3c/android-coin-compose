package com.nikec.coincompose.core.navigation.directions

import androidx.navigation.compose.NamedNavArgument
import com.nikec.coincompose.core.navigation.NavigationCommand

object CoinsDirections {

    const val COIN_ID = "coinId"

    val root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "root"

        override val route = destination
    }

    val coinsList = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coins"

        override val route = destination
    }

    fun coin(coinId: String? = null): NavigationCommand {
        return object : NavigationCommand {

            override val arguments = emptyList<NamedNavArgument>()

            override val destination = "coin/$coinId"

            override val route = "coin/{$COIN_ID}"
        }
    }
}
