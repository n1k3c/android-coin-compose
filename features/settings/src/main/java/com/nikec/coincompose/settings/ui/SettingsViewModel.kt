package com.nikec.coincompose.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.domain.GetCurrencyUseCase
import com.nikec.coincompose.core.domain.SaveCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getCurrencyUseCase: GetCurrencyUseCase,
    private val saveCurrencyUseCase: SaveCurrencyUseCase
) : ViewModel() {

    init {
        getCurrencyUseCase.invoke(Unit)
    }

    val currency: StateFlow<Currency?> = getCurrencyUseCase.flow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun onCurrencyChange(currency: Currency) {
        viewModelScope.launch {
            saveCurrencyUseCase.execute(currency)
        }
    }
}
