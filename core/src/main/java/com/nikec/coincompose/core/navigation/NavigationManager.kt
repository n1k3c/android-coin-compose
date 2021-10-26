/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
