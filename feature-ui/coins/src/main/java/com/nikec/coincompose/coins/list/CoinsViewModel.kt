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

package com.nikec.coincompose.coins.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.model.Currency
import com.nikec.coincompose.domain.usecases.FetchCoinsUseCase
import com.nikec.coincompose.domain.usecases.GetCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val PAGE_SIZE = 35
private const val INITIAL_LOAD_SIZE = PAGE_SIZE
private const val MAX_PAGES = 6

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    fetchCoinsUseCase: FetchCoinsUseCase,
    getCurrencyUseCase: GetCurrencyUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = INITIAL_LOAD_SIZE,
        enablePlaceholders = true
    )

    private val coinListEventsChannel = Channel<CoinListEvent>(Channel.CONFLATED)
    val coinListEvents: Flow<CoinListEvent> = coinListEventsChannel.receiveAsFlow()

    init {
        getCurrencyUseCase.invoke(Unit)
    }

    val currency: StateFlow<Currency?> = getCurrencyUseCase.flow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val paginatedCoins: Flow<PagingData<Coin>> =
        currency.filterNotNull().flatMapLatest { currency ->
            fetchCoinsUseCase.invoke(
                FetchCoinsUseCase.Params(
                    pagingConfig = pagingConfig,
                    currency = currency,
                    maxPages = MAX_PAGES
                )
            )
            fetchCoinsUseCase.flow
        }.cachedIn(viewModelScope)

    fun onCoinClicked(coin: Coin) {
        navigationManager.navigate(CoinsDirections.coinDetails(coin.id))
    }

    fun onScrollToTopClicked() {
        coinListEventsChannel.trySend(CoinListEvent.ScrollToTop)
    }

    fun onRefresh() {
        coinListEventsChannel.trySend(CoinListEvent.Refresh)
    }

    fun onRetryClicked() {
        coinListEventsChannel.trySend(CoinListEvent.Retry)
    }
}

sealed class CoinListEvent {
    object Refresh : CoinListEvent()
    object ScrollToTop : CoinListEvent()
    object Retry : CoinListEvent()
}
