package com.nikec.coincompose.core.domain

import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.data.repository.SettingsRepository
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val settingsRepository: SettingsRepository
) {
    fun execute(): Flow<Currency> =
        settingsRepository.getCurrency().flowOn(coroutineContextProvider.io)
}
