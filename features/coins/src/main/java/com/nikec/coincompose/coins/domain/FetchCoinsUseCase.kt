package com.nikec.coincompose.coins.domain

import com.nikec.coincompose.coins.data.repository.CoinsRepository
import javax.inject.Inject

class FetchCoinsUseCase @Inject constructor(private val coinsRepository: CoinsRepository) {

    fun execute() = coinsRepository.fetchCoins()
}
