package com.nikec.coincompose.coins.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikec.coincompose.coins.navigation.CoinsDirections
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
