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

package com.nikec.coincompose.coins.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.model.Currency
import com.nikec.coincompose.domain.usecases.GetCoinUseCase
import com.nikec.coincompose.domain.usecases.GetCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    getCoinUseCase: GetCoinUseCase,
    getCurrencyUseCase: GetCurrencyUseCase
) : ViewModel() {

    private val coinId = savedStateHandle.get<String>(CoinsDirections.COIN_ID)
        ?: throw IllegalStateException("Coin ID can not be null.")

    init {
        getCoinUseCase.invoke(GetCoinUseCase.Params(coinId = coinId))
        getCurrencyUseCase.invoke(Unit)
    }

    val state: StateFlow<CoinUiState> = combine(
        getCoinUseCase.flow,
        getCurrencyUseCase.flow
    ) { coinResult, currency ->
        CoinUiState(coin = coinResult, currency = currency)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CoinUiState()
    )

    fun onBackClicked() {
        navigationManager.navigateBack()
    }
}

data class CoinUiState(
    val coin: Coin? = null,
    val currency: Currency = Currency.USD
)
