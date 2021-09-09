package com.nikec.coincompose.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikec.coincompose.core.db.CoinsDatabase
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
@Entity(tableName = CoinsDatabase.COINS_TABLE)
data class Coin(
    @PrimaryKey
    val id: String,
    val ath: Double,
    @Json(name = "ath_date")
    val athDate: LocalDateTime?,
    val atl: Double,
    @Json(name = "atl_date")
    val atlDate: LocalDateTime?,
    @Json(name = "current_price")
    val currentPrice: Double,
    @Json(name = "high_24")
    val high24h: Double?,
    val image: String,
    @Json(name = "low_24")
    val low24h: Double?,
    @Json(name = "market_cap")
    val marketCap: Long,
    val name: String,
    @Json(name = "price_change_percentage_1h_in_currency")
    val priceChangePercentage1h: Double?,
    @Json(name = "price_change_percentage_24h_in_currency")
    val priceChangePercentage24h: Double?,
    @Json(name = "price_change_percentage_7d_in_currency")
    val priceChangePercentage7d: Double?,
    @Json(name = "price_change_percentage_30d_in_currency")
    val priceChangePercentage30d: Double?,
    @Json(name = "price_change_percentage_1y_in_currency")
    val priceChangePercentage1y: Double?,
    val symbol: String,
    @Json(name = "sparkline_in_7d")
    val sparkline: SparklineIn7d
)

data class SparklineIn7d(
    val price: List<Double>
)
