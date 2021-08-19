package com.nikec.coincompose.coins.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.coins.data.paging.CoinsPageKeyedRemoteMediator
import com.nikec.coincompose.core.db.CoinsDao
import com.nikec.coincompose.core.db.CoinsDatabase
import com.nikec.coincompose.core.db.CoinsRemoteKeysDao
import com.nikec.coincompose.core.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 20

interface CoinsRepository {
    fun fetchCoins(): Flow<PagingData<Coin>>
}

class CoinsRepositoryImpl @Inject constructor(
    private val db: CoinsDatabase,
    private val coinsDao: CoinsDao,
    private val coinsRemoteKeysDao: CoinsRemoteKeysDao,
    private val coinsService: CoinsService
) : CoinsRepository {

    @ExperimentalPagingApi
    override fun fetchCoins() = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE),
        remoteMediator = CoinsPageKeyedRemoteMediator(
            db,
            coinsDao,
            coinsRemoteKeysDao,
            coinsService
        )
    ) {
        coinsDao.observeCoinsPaginated()
    }.flow
}
