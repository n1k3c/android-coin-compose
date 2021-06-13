package com.nikec.coincompose.domain.coins

import androidx.paging.PagingData
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.model.CoinShort
import com.nikec.coincompose.data.repository.CoinsRepository
import com.nikec.coincompose.domain.BaseUseCase
import com.nikec.coincompose.domain.SuspendUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
): BaseUseCase<Unit, Flow<PagingData<CoinShort>>>() {

    override fun execute(parameters: Unit) = coinsRepository.fetchCoins()
}
