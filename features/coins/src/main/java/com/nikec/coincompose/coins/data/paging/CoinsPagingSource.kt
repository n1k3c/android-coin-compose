package com.nikec.coincompose.coins.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.coins.data.api.CoinsService
import com.nikec.coincompose.core.db.CoinsDatabase
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall

class CoinsPagingSource(
    private val db: CoinsDatabase,
    private val coinsService: CoinsService
) : PagingSource<Int, Coin>() {

    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val key = params.key ?: 1
        return when (val result = safeApiCall { coinsService.fetchCoins(page = key) }) {
            is Result.Success -> {
                val nextKey = key + 1

                if (result.payload.isEmpty()) {
                    i { "Last page loaded" }
                    LoadResult.Page(result.payload, null, null)
                } else {
                    i { "Page number $nextKey is loading..." }
                    LoadResult.Page(result.payload, null, nextKey)
                }
            }
            is Result.HttpError -> {
                e { "Http error -> " + result.exception.toString() }
                LoadResult.Error(result.exception)
            }
            Result.NetworkError -> {
                e { "Network error" }
                LoadResult.Error(Throwable("No internet"))
            }
            is Result.UnknownError -> {
                e { "Unknown error -> " + result.throwable.toString() }
                LoadResult.Error(result.throwable)
            }
        }
    }
}
