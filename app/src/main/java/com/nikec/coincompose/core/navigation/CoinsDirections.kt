package com.nikec.coincompose.core.navigation

import androidx.navigation.compose.NamedNavArgument

object CoinsDirections {

    val root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "root"
    }

    val coinsList = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coins"
    }

    val coin = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coin/{coinId}"
    }

    fun coin(coinId: String): NavigationCommand {
        return object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()

            override val destination = "coin/$coinId"
        }
    }
}
