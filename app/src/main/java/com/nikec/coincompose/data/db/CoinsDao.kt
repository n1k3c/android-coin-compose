package com.nikec.coincompose.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikec.coincompose.data.model.Coin

@Dao
interface CoinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Query("SELECT * FROM ${CoinsDatabase.COINS_TABLE}")
    fun observeCoinsPaginated(): PagingSource<Int, Coin>

    @Query("DELETE FROM ${CoinsDatabase.COINS_TABLE}")
    fun deleteAll(): Int
}
