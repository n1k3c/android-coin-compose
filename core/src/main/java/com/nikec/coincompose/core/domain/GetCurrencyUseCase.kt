package com.nikec.coincompose.core.domain

import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.data.repository.SettingsRepository
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.ObservableUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val settingsRepository: SettingsRepository
) : ObservableUseCase<Unit, Currency>() {

    override fun execute(params: Unit): Flow<Currency> =
        settingsRepository.getCurrency().flowOn(coroutineContextProvider.io)
}
