package com.nikec.coincompose.domain.usecases

import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.repository.CoinsRepository
import com.nikec.coincompose.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val coinsRepository: CoinsRepository
) : SubjectInteractor<GetCoinUseCase.Params, Coin?>() {

    override fun createObservable(params: Params): Flow<Coin?> =
        coinsRepository.getCoin(params.coinId).flowOn(coroutineContextProvider.io)

    data class Params(val coinId: String)
}
