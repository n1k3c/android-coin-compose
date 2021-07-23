package com.nikec.coincompose.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikec.coincompose.core.db.CoinsDatabase

@Entity(tableName = CoinsDatabase.COINS_REMOTE_KEYS_TABLE)
data class CoinRemoteKeys(
        @PrimaryKey
        val coinId: String,
        val prevKey: Int?,
        val nextKey: Int?
)
