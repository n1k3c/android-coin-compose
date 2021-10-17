package com.nikec.coincompose.core.navigation

import androidx.navigation.compose.NamedNavArgument

object SettingsDirections {

    val root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "settings"

        override val route = destination
    }

    val settings = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "settingsView"

        override val route = destination
    }
}
