package com.nikec.coincompose.core.navigation

import androidx.navigation.compose.NamedNavArgument
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

const val DESTINATION_BACK: String = "back"

class NavigationManager {

    private val eventChannel = Channel<NavigationCommand>(Channel.CONFLATED)
    val commandFlow: Flow<NavigationCommand> = eventChannel.receiveAsFlow()

    fun navigate(directions: NavigationCommand) {
        eventChannel.trySend(element = directions)
    }

    fun navigateBack() {
        eventChannel.trySend(object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()

            override val destination = DESTINATION_BACK

            override val route = ""
        })
    }
}