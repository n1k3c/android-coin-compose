package com.nikec.coincompose.coins.domain

import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val coinsRepository: CoinsRepository) {

    suspend fun execute(coinId: String): Result<Flow<Coin?>> {
        return safeApiCall { coinsRepository.getCoin(coinId) }
    }
}
