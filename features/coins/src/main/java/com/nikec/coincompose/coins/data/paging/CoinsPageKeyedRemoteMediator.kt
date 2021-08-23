package com.nikec.coincompose.coins.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.ajalt.timberkt.Timber.e
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.core.db.CoinsDatabase
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.model.CoinRemoteKeys
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import kotlinx.coroutines.withContext
import java.io.InvalidObjectException

@ExperimentalPagingApi
class CoinsPageKeyedRemoteMediator(
    private val db: CoinsDatabase,
    private val coinsService: CoinsService,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val maxPages: Int
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

        return when (val result =
            withContext(coroutineContextProvider.io) { safeApiCall { coinsService.fetchCoins(page = page) } }) {
            is Result.Success -> {
                i { "Page -> " + page }
                val endOfPaginationReached = page == maxPages

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
            Result.NetworkError -> {
                e { "Network error" }
                MediatorResult.Error(Throwable("No internet"))
            }
            is Result.UnknownError -> {
                e { "Unknown error -> " + result.throwable.toString() }
                MediatorResult.Error(result.throwable)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Coin>): CoinRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { coin ->
                // Get the remote keys of the last item retrieved
                withContext(coroutineContextProvider.io) {
                    db.coinsRemoteKeysDao().getRemoteKeysByCoinId(coin.id)
                }
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Coin>): CoinRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { coin ->
                // Get the remote keys of the first items retrieved
                withContext(coroutineContextProvider.io) {
                    db.coinsRemoteKeysDao().getRemoteKeysByCoinId(coin.id)
                }
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Coin>
    ): CoinRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { coinId ->
                withContext(coroutineContextProvider.io) {
                    db.coinsRemoteKeysDao().getRemoteKeysByCoinId(coinId)
                }
            }
        }
    }
}