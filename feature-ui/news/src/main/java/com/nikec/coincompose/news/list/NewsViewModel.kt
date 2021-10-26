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

package com.nikec.coincompose.news.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nikec.coincompose.data.model.News
import com.nikec.coincompose.domain.usecases.FetchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = PAGE_SIZE

@HiltViewModel
class NewsViewModel @Inject constructor(
    fetchNewsUseCase: FetchNewsUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        enablePlaceholders = true
    )

    private val newsListEventsChannel = Channel<NewsListEvent>(Channel.CONFLATED)
    val newsListEvents: Flow<NewsListEvent> = newsListEventsChannel.receiveAsFlow()

    init {
        fetchNewsUseCase(FetchNewsUseCase.Params(pagingConfig = pagingConfig))
    }

    val paginatedNews = fetchNewsUseCase.flow.cachedIn(viewModelScope)

    fun onNewsClicked(news: News) {
        newsListEventsChannel.trySend(NewsListEvent.OpenDetails(news))
    }

    fun onRefresh() {
        newsListEventsChannel.trySend(NewsListEvent.Refresh)
    }

    fun onRetryClicked() {
        newsListEventsChannel.trySend(NewsListEvent.Retry)
    }
}

sealed class NewsListEvent {
    object Refresh : NewsListEvent()
    object Retry : NewsListEvent()
    data class OpenDetails(val news: News) : NewsListEvent()
}
