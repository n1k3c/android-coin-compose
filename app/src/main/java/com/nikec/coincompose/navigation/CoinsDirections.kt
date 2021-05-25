package com.nikec.coincompose.navigation

import androidx.navigation.compose.NamedNavArgument

object CoinsDirections {

    val root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "root"
    }

    val coins = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coins"
    }

    val coin = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coin"
    }
}
