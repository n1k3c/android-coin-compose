package com.nikec.coincompose.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.core.data.repository.SettingsRepository
import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.domain.GetCurrencyUseCase
import com.nikec.coincompose.core.domain.SaveCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getCurrencyUseCase: GetCurrencyUseCase,
    private val saveCurrencyUseCase: SaveCurrencyUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getCurrencyUseCase.execute().collect { currency ->
                i { "Currency -> $currency" }
            }
        }
    }

    fun onCurrencyChange(currency: Currency) {
        viewModelScope.launch {
            saveCurrencyUseCase.execute(currency)
        }
    }
}
