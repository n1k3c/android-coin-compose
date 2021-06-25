package com.nikec.coincompose.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikec.coincompose.data.model.CoinRemoteKeys

@Dao
interface CoinsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<CoinRemoteKeys>)

    @Query("SELECT * FROM ${CoinsDatabase.COINS_REMOTE_KEYS_TABLE} WHERE coinId = :coinId")
    fun getRemoteKeysByCoinId(coinId: String): CoinRemoteKeys?

    @Query("DELETE FROM ${CoinsDatabase.COINS_REMOTE_KEYS_TABLE}")
    fun deleteAll()
}
