package com.nikec.coincompose.domain.coins

import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.repository.CoinsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val coinsRepository: CoinsRepository
) {

    suspend fun execute() {
        return withContext(coroutineContextProvider.io) {
            coinsRepository.fetchCoins()
        }
    }
}
