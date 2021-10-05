package com.nikec.coincompose.core.domain

import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.data.repository.SettingsRepository
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCurrencyUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(currency: Currency) = withContext(coroutineContextProvider.io) {
        settingsRepository.setCurrency(currency)
    }
}
