package com.nikec.coincompose.core.navigation

import androidx.navigation.compose.NamedNavArgument

interface NavigationCommand {

    val arguments: List<NamedNavArgument>

    val destination: String

    val route: String
}
