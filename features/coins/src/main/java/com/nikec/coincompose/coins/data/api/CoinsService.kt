package com.nikec.coincompose.coins.data.api

import com.nikec.coincompose.core.model.Coin
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsService {

    @GET("api/v3/coins/markets")
    suspend fun fetchCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = true,
        @Query("price_change_percentage") priceChangePercentage: String = "1h,24h,7d,30d,1y",
    ): List<Coin>
}
