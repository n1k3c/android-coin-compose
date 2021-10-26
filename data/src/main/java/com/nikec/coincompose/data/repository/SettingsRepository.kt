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

package com.nikec.coincompose.data.repository

import androidx.datastore.core.DataStore
import com.github.ajalt.timberkt.e
import com.nikec.coincompose.core.Settings
import com.nikec.coincompose.data.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface SettingsRepository {
    fun getCurrency(): Flow<Currency>
    suspend fun setCurrency(currency: Currency): String?
}

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: DataStore<Settings>
) : SettingsRepository {

    private val settingsFlow = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                e { "Error reading settings data store. Exception: $exception" }
                emit(Settings.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override fun getCurrency(): Flow<Currency> = settingsFlow.map { settings ->
        Currency.values().firstOrNull { settings.currency == it.key }
            ?: Currency.USD // Default value
    }

    override suspend fun setCurrency(currency: Currency): String? {
        return settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setCurrency(currency.key)
                .build()
        }.currency
    }
}
