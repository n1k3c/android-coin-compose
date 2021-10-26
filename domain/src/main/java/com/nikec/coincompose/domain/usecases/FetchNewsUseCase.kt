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

package com.nikec.coincompose.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.data.api.NewsService
import com.nikec.coincompose.data.model.News
import com.nikec.coincompose.data.paging.NewsPagingSource
import com.nikec.coincompose.data.repository.NewsRepository
import com.nikec.coincompose.domain.PagingInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val newsRepository: NewsRepository,
    private val newsService: NewsService
) : PagingInteractor<FetchNewsUseCase.Params, News>() {

    override fun createObservable(params: Params): Flow<PagingData<News>> {
        return Pager(
            config = params.pagingConfig,
            pagingSourceFactory = {
                NewsPagingSource(
                    newsService = newsService
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { news ->
                val newsImage = withContext(coroutineContextProvider.io) {
                    newsRepository.fetchNewsImage(news)
                }
                news.copy(image = newsImage)
            }
        }.flowOn(coroutineContextProvider.io)
    }

    data class Params(override val pagingConfig: PagingConfig) : PagingInteractor.Params<News>
}
