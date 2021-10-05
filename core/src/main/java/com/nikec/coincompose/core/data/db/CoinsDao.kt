package com.nikec.coincompose.core.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikec.coincompose.core.data.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Query("SELECT * FROM ${CoinsDatabase.COINS_TABLE}")
    fun observeCoinsPaginated(): PagingSource<Int, Coin>

    @Query("DELETE FROM ${CoinsDatabase.COINS_TABLE}")
    fun deleteAll(): Int

    @Query("SELECT * FROM ${CoinsDatabase.COINS_TABLE} WHERE id = :coinId")
    fun getCoin(coinId: String): Flow<Coin?>
}
