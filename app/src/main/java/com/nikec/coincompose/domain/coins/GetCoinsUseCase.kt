package com.nikec.coincompose.domain.coins

import androidx.paging.PagingData
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.repository.CoinsRepository
import com.nikec.coincompose.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
): BaseUseCase<Unit, Flow<PagingData<Coin>>>() {

    override fun execute(parameters: Unit) = coinsRepository.fetchCoins()
}
