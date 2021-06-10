package com.nikec.coincompose.data.repository

import com.nikec.coincompose.data.api.ApiService
import com.nikec.coincompose.data.model.CoinShort
import javax.inject.Inject

interface CoinsRepository {
    suspend fun fetchCoins(): List<CoinShort>
}

class CoinsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CoinsRepository {

    override suspend fun fetchCoins() = apiService.fetchCoins()
}
