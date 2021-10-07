package com.nikec.coincompose.coins.data.repository

import androidx.paging.*
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.coins.data.paging.CoinsPageKeyedRemoteMediator
import com.nikec.coincompose.core.data.db.CoinsDatabase
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.data.model.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CoinsRepository {
    fun getCoins(): PagingSource<Int, Coin>
    fun getCoin(coinId: String): Flow<Coin?>
}

class CoinsRepositoryImpl @Inject constructor(
    private val db: CoinsDatabase,
    private val coinsService: CoinsService
) : CoinsRepository {

    override fun getCoins(): PagingSource<Int, Coin> = db.coinsDao().observeCoinsPaginated()

    override fun getCoin(coinId: String): Flow<Coin?> = db.coinsDao().getCoin(coinId)
}
