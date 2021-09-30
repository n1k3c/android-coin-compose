package com.nikec.coincompose.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikec.coincompose.core.data.db.CoinsDatabase

@Entity(tableName = CoinsDatabase.COINS_REMOTE_KEYS_TABLE)
data class CoinRemoteKeys(
    @PrimaryKey
    val coinId: String,
    val prevKey: Int?,
    val nextKey: Int?
)