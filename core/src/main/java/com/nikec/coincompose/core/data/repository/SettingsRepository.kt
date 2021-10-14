package com.nikec.coincompose.core.data.repository

import androidx.datastore.core.DataStore
import com.github.ajalt.timberkt.e
import com.nikec.coincompose.core.Settings
import com.nikec.coincompose.core.data.model.Currency
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
