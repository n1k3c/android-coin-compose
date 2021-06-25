package com.nikec.coincompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikec.coincompose.data.api.ApiService
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.paging.CoinsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = 20

interface CoinsRepository {
    fun fetchCoins(): Flow<PagingData<Coin>>
}

class CoinsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CoinsRepository {

    override fun fetchCoins() = Pager(
        PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE),
        pagingSourceFactory = {
            CoinsPagingSource(apiService)
        }
    ).flow
}
