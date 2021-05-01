package com.nikec.coincompose.navigation

import androidx.navigation.compose.NamedNavArgument
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    val default = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = ""

    }

    var commands = MutableStateFlow(default)

    fun navigate(directions: NavigationCommand) {
        commands.value = directions
    }
}