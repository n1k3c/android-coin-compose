package com.nikec.coincompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import com.nikec.coincompose.data.api.ApiService
import com.nikec.coincompose.data.model.CoinShort

class CoinsPagingSource(private val apiService: ApiService) : PagingSource<Int, CoinShort>() {

    override fun getRefreshKey(state: PagingState<Int, CoinShort>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinShort> {
        val key = params.key ?: 1
        return when (val result = safeApiCall { apiService.fetchCoins(page = key) }) {
            is Result.Success -> {
                val nextKey = key + 1

                 if(result.payload.isEmpty()) {
                     d { "Done" }
                     LoadResult.Page(result.payload, null, null)
                 } else {
                     d { "Page is loading... " + nextKey }
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
