package com.nikec.coincompose.domain.usecases

import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.model.Currency
import com.nikec.coincompose.data.repository.SettingsRepository
import com.nikec.coincompose.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val settingsRepository: SettingsRepository
) : SubjectInteractor<Unit, Currency>() {

    override fun createObservable(params: Unit): Flow<Currency> =
        settingsRepository.getCurrency().flowOn(coroutineContextProvider.io)
}
