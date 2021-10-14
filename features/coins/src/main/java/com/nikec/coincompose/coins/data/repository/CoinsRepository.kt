package com.nikec.coincompose.coins.data.repository

import androidx.paging.PagingSource
import com.nikec.coincompose.core.data.db.CoinsDatabase
import com.nikec.coincompose.core.data.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CoinsRepository {
    fun getCoins(): PagingSource<Int, Coin>
    fun getCoin(coinId: String): Flow<Coin?>
}

class CoinsRepositoryImpl @Inject constructor(
    private val db: CoinsDatabase
) : CoinsRepository {

    override fun getCoins(): PagingSource<Int, Coin> = db.coinsDao().observeCoinsPaginated()

    override fun getCoin(coinId: String): Flow<Coin?> = db.coinsDao().getCoin(coinId)
}
