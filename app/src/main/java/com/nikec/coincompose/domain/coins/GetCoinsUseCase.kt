package com.nikec.coincompose.domain.coins

import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.model.CoinShort
import com.nikec.coincompose.data.repository.CoinsRepository
import com.nikec.coincompose.domain.SuspendUseCase
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider,
    private val coinsRepository: CoinsRepository
): SuspendUseCase<Unit, List<CoinShort>>(coroutineContextProvider.io) {

    override suspend fun execute(parameters: Unit) = coinsRepository.fetchCoins()
}
