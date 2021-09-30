package com.nikec.coincompose.core.domain

import com.nikec.coincompose.core.data.repository.SettingsRepository
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute() = withContext(coroutineContextProvider.io) {
        settingsRepository.getCurrency()
    }
}
