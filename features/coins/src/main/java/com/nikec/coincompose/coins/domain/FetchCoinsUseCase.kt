package com.nikec.coincompose.coins.domain

import androidx.paging.PagingData
import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCoinsUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val coinsRepository: CoinsRepository
) {

    fun execute(currency: Currency): Flow<PagingData<Coin>> =
        coinsRepository.fetchCoins(currency = currency).flowOn(coroutineContextProvider.io)
}
