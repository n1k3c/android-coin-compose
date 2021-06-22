package com.nikec.coincompose.data.model

data class CoinShort(
    val ath: Double,
    val ath_date: String,
    val atl: Double,
    val atl_date: String,
    val current_price: Double,
    val high_24h: Double?,
    val id: String,
    val image: String,
    val low_24h: Double?,
    val market_cap: Long,
    val name: String,
    val price_change_24h: Double?,
    val price_change_percentage_24h: Double?,
    val symbol: String,
)