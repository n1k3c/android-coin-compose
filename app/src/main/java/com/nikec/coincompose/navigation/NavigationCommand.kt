package com.nikec.coincompose.navigation

import androidx.navigation.compose.NamedNavArgument

interface NavigationCommand {

    val arguments: List<NamedNavArgument>

    val destination: String
}
