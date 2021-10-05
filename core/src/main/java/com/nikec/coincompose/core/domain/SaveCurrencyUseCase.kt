package com.nikec.coincompose.core.domain

import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.data.repository.SettingsRepository
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.ResultInteractor
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCurrencyUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val settingsRepository: SettingsRepository
) : ResultInteractor<SaveCurrencyUseCase.Params, String?>() {

    override suspend fun doWork(params: Params): String? =
        withContext(coroutineContextProvider.io) {
            settingsRepository.setCurrency(params.currency)
        }

    data class Params(val currency: Currency)
}
