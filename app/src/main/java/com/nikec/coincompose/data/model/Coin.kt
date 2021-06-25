package com.nikec.coincompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikec.coincompose.data.db.CoinsDatabase
import com.squareup.moshi.Json

@Entity(tableName = CoinsDatabase.COINS_TABLE)
data class Coin(
    @PrimaryKey
    val id: String,
    val ath: Double,
    @Json(name = "ath_date")
    val athDate: String?,
    val atl: Double,
    @Json(name = "atl_date")
    val atlDate: String?,
    val current_price: Double,
    @Json(name = "high_24")
    val high24h: Double?,
    val image: String,
    @Json(name = "low_24")
    val low24h: Double?,
    @Json(name = "market_cap")
    val marketCap: Long,
    val name: String,
    @Json(name = "price_change_24h")
    val priceChange24h: Double?,
    @Json(name = "price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    val symbol: String,
)
