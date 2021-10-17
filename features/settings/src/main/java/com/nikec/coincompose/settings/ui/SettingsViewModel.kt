package com.nikec.coincompose.settings.ui

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
