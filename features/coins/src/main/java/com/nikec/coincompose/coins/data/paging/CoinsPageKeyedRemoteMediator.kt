package com.nikec.coincompose.coins.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.ajalt.timberkt.Timber.e
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.core.db.CoinsDatabase
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.model.CoinRemoteKeys
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CoinsPageKeyedRemoteMediator @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val db: CoinsDatabase,
    private val coinsService: CoinsService
) : RemoteMediator<Int, Coin>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Coin>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: throw InvalidObjectException("Result is empty")
                remoteKeys.nextKey ?: return MediatorResult.Success(true)
            }
        }

        return when (val result = safeApiCall(coroutineContextProvider.io) {
            coinsService.fetchCoins(
                page = page,
                perPage = state.config.pageSize
            )
        }) {
            is Result.Success -> {
                val endOfPaginationReached = page == CoinsRepository.MAX_PAGES

                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.coinsRemoteKeysDao().deleteAll()
                        db.coinsDao().deleteAll()
                    }

                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val keys = result.payload.map {
                        CoinRemoteKeys(coinId = it.id, prevKey = prevKey, nextKey = nextKey)
                    }

                    db.coinsRemoteKeysDao().insertAll(keys)
                    db.coinsDao().insertAll(result.payload)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
            is Result.HttpError -> {
                e { "Http error -> " + result.exception.toString() }
                MediatorResult.Error(result.exception)
            }
            is Result.NetworkError -> {
                e { "Network error" }
                MediatorResult.Error(result.noInternetThrowable)
            }
            is Result.UnknownError -> {
                e { "Unknown error -> " + result.throwable.toString() }
                MediatorResult.Error(result.throwable)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Coin>): CoinRemoteKeys? {
        return if (state.isEmpty()) {
            db.withTransaction { db.coinsRemoteKeysDao().getAll().lastOrNull() }
        } else {
            state.lastItemOrNull()?.let { coin ->
                db.withTransaction { db.coinsRemoteKeysDao().getRemoteKeysByCoinId(coin.id) }
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Coin>
    ): CoinRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.withTransaction { db.coinsRemoteKeysDao().getRemoteKeysByCoinId(id) }
            }
        }
    }
}
