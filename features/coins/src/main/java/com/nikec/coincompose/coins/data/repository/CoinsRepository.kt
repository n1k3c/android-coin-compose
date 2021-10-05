package com.nikec.coincompose.coins.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.coins.data.paging.CoinsPageKeyedRemoteMediator
import com.nikec.coincompose.core.data.db.CoinsDatabase
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.data.model.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 35
private const val INITIAL_LOAD_SIZE = PAGE_SIZE

interface CoinsRepository {
    companion object {
        const val MAX_PAGES = 6
    }

    fun fetchCoins(currency: Currency): Flow<PagingData<Coin>>
    fun getCoin(coinId: String): Flow<Coin?>
}

class CoinsRepositoryImpl @Inject constructor(
    private val db: CoinsDatabase,
    private val coinsService: CoinsService
) : CoinsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun fetchCoins(currency: Currency) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_LOAD_SIZE,
            enablePlaceholders = true
        ),
        remoteMediator = CoinsPageKeyedRemoteMediator(
            db = db,
            coinsService = coinsService,
            currency = currency
        )
    ) {
        db.coinsDao().observeCoinsPaginated()
    }.flow

    override fun getCoin(coinId: String): Flow<Coin?> = db.coinsDao().getCoin(coinId)
}
