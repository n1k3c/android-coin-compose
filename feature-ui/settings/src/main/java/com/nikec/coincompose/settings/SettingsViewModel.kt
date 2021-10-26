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

package com.nikec.coincompose.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikec.coincompose.data.model.Currency
import com.nikec.coincompose.domain.usecases.GetCurrencyUseCase
import com.nikec.coincompose.domain.usecases.SaveCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getCurrencyUseCase: GetCurrencyUseCase,
    private val saveCurrencyUseCase: SaveCurrencyUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getCurrencyUseCase.invoke(Unit)
        }
    }

    private val setCurrencyResult: MutableStateFlow<String?> = MutableStateFlow("")

    val currency: StateFlow<Currency?> = combine(
        getCurrencyUseCase.flow,
        setCurrencyResult
    ) { currency, _ ->
        // Right now we don't need currencyResult; this is just example how we can use
        // MutableStateFlow to hold a value and combine results...
        currency
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(FlowPreview::class)
    fun onCurrencyChange(currency: Currency) {
        viewModelScope.launch {
            saveCurrencyUseCase.invoke(SaveCurrencyUseCase.Params(currency = currency)).collect {
                setCurrencyResult.emit(it)
            }
        }
    }
}
