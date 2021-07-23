package com.nikec.coincompose.coins.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikec.coincompose.coins.data.api.ApiService
import com.nikec.coincompose.core.db.CoinsDatabase
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.coins.data.paging.CoinsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 20

interface CoinsRepository {
    fun fetchCoins(): Flow<PagingData<Coin>>
}

class CoinsRepositoryImpl @Inject constructor(
    private val db: CoinsDatabase,
    private val apiService: ApiService
) : CoinsRepository {

    override fun fetchCoins() = Pager(
        PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE),
        pagingSourceFactory = {
            CoinsPagingSource(db, apiService)
        }
    ).flow
}
