package com.nikec.coincompose.coins.domain

import androidx.paging.PagingData
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.core.utils.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
): BaseUseCase<Unit, Flow<PagingData<Coin>>>() {

    override fun execute(parameters: Unit) = coinsRepository.fetchCoins()
}
