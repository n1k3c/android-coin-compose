package com.nikec.coincompose.core.navigation

import androidx.navigation.compose.NamedNavArgument

object NewsDirections {

    val root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "news"

        override val route = destination
    }

    val newsList = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "newsList"

        override val route = destination
    }
}
