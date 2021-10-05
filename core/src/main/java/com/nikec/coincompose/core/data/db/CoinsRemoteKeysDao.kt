package com.nikec.coincompose.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikec.coincompose.core.data.model.CoinRemoteKeys

@Dao
interface CoinsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CoinRemoteKeys>)

    @Query("SELECT * FROM ${CoinsDatabase.COINS_REMOTE_KEYS_TABLE} WHERE coinId = :coinId")
    suspend fun getRemoteKeysByCoinId(coinId: String): CoinRemoteKeys?

    @Query("SELECT * FROM ${CoinsDatabase.COINS_REMOTE_KEYS_TABLE}")
    suspend fun getAll(): List<CoinRemoteKeys>

    @Query("DELETE FROM ${CoinsDatabase.COINS_REMOTE_KEYS_TABLE}")
    fun deleteAll()
}
