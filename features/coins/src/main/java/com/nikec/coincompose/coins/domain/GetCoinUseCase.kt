package com.nikec.coincompose.coins.domain

import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.SuspendUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val coinsRepository: CoinsRepository
) : SuspendUseCase<String, Flow<Coin?>>(coroutineContextProvider.io) {

    override suspend fun execute(parameters: String): Flow<Coin?> =
        coinsRepository.getCoin(parameters)
}
