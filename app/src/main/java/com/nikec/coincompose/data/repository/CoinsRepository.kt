package com.nikec.coincompose.data.repository

import com.github.ajalt.timberkt.d
import com.nikec.coincompose.data.api.ApiService
import javax.inject.Inject

interface CoinsRepository {
    suspend fun fetchCoins()
}

class CoinsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CoinsRepository {

    override suspend fun fetchCoins() {
        d { "fetching coins..." }
    }
}
