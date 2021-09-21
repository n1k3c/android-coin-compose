package com.nikec.coincompose.news.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.ajalt.timberkt.e
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import com.nikec.coincompose.news.data.api.NewsService
import com.nikec.coincompose.news.data.model.News
import kotlinx.coroutines.delay
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val newsService: NewsService,
) : PagingSource<Int, News>() {

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val key = params.key ?: 1

        val result = safeApiCall {
            if (key != 1) delay(1000)
            newsService.fetchNews(page = key)
        }

        return when (result) {
            is Result.Success -> {
                val nextKey = if (result.payload.next == null) null else key + 1

                LoadResult.Page(
                    data = result.payload.results,
                    nextKey = nextKey,
                    prevKey = null
                )
            }
            is Result.HttpError -> {
                e { "Http error -> " + result.exception.toString() }
                LoadResult.Error(result.exception)
            }
            is Result.NetworkError -> {
                e { "Network error" }
                LoadResult.Error(result.noInternetThrowable)
            }
            is Result.UnknownError -> {
                e { "Unknown error -> " + result.throwable.toString() }
                LoadResult.Error(result.throwable)
            }
        }
    }
}
