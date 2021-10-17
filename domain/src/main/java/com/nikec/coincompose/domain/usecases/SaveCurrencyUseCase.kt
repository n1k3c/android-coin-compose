package com.nikec.coincompose.domain.usecases

import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.model.Currency
import com.nikec.coincompose.data.repository.SettingsRepository
import com.nikec.coincompose.domain.ResultInteractor
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
