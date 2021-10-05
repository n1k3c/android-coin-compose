package com.nikec.coincompose.coins.domain

import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.ObservableUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val coinsRepository: CoinsRepository
) : ObservableUseCase<GetCoinUseCase.Params, Coin?>() {

    override fun execute(params: Params): Flow<Coin?> =
        coinsRepository.getCoin(params.coinId).flowOn(coroutineContextProvider.io)

    data class Params(val coinId: String)
}
