/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nikec.coincompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikec.coincompose.data.db.CoinsDatabase
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
    @Json(name = "high_24h")
    val high24h: Double?,
    val image: String,
    @Json(name = "low_24h")
    val low24h: Double?,
    @Json(name = "market_cap")
    val marketCap: Long,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int,
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
