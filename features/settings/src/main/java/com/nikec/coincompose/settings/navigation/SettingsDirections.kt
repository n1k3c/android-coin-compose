package com.nikec.coincompose.settings.navigation

import androidx.navigation.compose.NamedNavArgument
import com.nikec.coincompose.core.navigation.NavigationCommand

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
