package com.nikec.coincompose.coins.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.coins.data.paging.CoinsPageKeyedRemoteMediator
import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.core.data.db.CoinsDatabase
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.PagingInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCoinsUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val coinsRepository: CoinsRepository,
    private val db: CoinsDatabase,
    private val coinsService: CoinsService
) : PagingInteractor<FetchCoinsUseCase.Params, Coin>() {

    data class Params(
        override val pagingConfig: PagingConfig,
        val currency: Currency,
        val maxPages: Int
    ) : PagingInteractor.Params<Coin>

    @OptIn(ExperimentalPagingApi::class)
    override fun createObservable(params: Params): Flow<PagingData<Coin>> {
        return Pager(
            config = params.pagingConfig,
            remoteMediator = CoinsPageKeyedRemoteMediator(
                db = db,
                coinsService = coinsService,
                currency = params.currency,
                maxPages = params.maxPages
            )
        ) {
            coinsRepository.getCoins()
        }.flow.flowOn(coroutineContextProvider.io)
    }
}
