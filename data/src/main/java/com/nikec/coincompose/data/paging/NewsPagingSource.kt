/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nikec.coincompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.ajalt.timberkt.e
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import com.nikec.coincompose.data.api.NewsService
import com.nikec.coincompose.data.model.News
import kotlinx.coroutines.delay

class NewsPagingSource(
    private val newsService: NewsService
) : PagingSource<Int, News>() {

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val key = params.key ?: 1

        val result = safeApiCall {
            // Current API has some limits regarding traffic so we must put delay here
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
