package com.nikec.coincompose.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.data.model.CoinRemoteKeys

const val DATABASE_NAME = "coins.db"

@Database(
    entities = [Coin::class, CoinRemoteKeys::class],
    version = 1
)
abstract class CoinsDatabase : RoomDatabase() {

    companion object {
        const val COINS_TABLE = "coins"

        const val COINS_REMOTE_KEYS_TABLE = "coins_remote_keys"
    }

    abstract fun coinsDao(): CoinsDao

    abstract fun coinsRemoteKeysDao(): CoinsRemoteKeysDao
}
