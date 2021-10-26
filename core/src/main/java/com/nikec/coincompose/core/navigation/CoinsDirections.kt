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

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

object CoinsDirections {

    const val COIN_ID = "coinId"

    val root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coins"

        override val route = destination
    }

    val coinsList = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "coinsList"

        override val route = destination
    }

    fun coinDetails(coinId: String? = null): NavigationCommand {
        return object : NavigationCommand {

            override val arguments = listOf(
                navArgument(COIN_ID) { type = NavType.StringType }
            )

            override val destination = "coin/$coinId"

            override val route = "coin/{$COIN_ID}"
        }
    }
}
