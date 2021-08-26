package com.nikec.coincompose.coins.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.coins.data.paging.CoinsPageKeyedRemoteMediator
import com.nikec.coincompose.core.db.CoinsDatabase
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val MAX_PAGES = 6
private const val PAGE_SIZE = 35
private const val INITIAL_LOAD_SIZE = PAGE_SIZE

interface CoinsRepository {
    fun fetchCoins(): Flow<PagingData<Coin>>
}

class CoinsRepositoryImpl @Inject constructor(
    private val db: CoinsDatabase,
    private val coinsService: CoinsService,
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider()
) : CoinsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchCoins() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_LOAD_SIZE,
            enablePlaceholders = true
        ),
        remoteMediator = CoinsPageKeyedRemoteMediator(
            db,
            coinsService,
            coroutineContextProvider,
            MAX_PAGES
        )
    ) {
        db.coinsDao().observeCoinsPaginated()
    }.flow
}
