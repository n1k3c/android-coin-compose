package com.nikec.coincompose.coins.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikec.coincompose.coins.navigation.CoinsDirections
import com.nikec.coincompose.coins.domain.GetCoinUseCase
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.core.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    private val getCoinUseCase: GetCoinUseCase
) : ViewModel() {

    private val coinId = savedStateHandle.get<String>(CoinsDirections.COIN_ID)
        ?: throw IllegalStateException("Coin ID can not be null.")

    val state = flow {
        when (val result = getCoinUseCase.invoke(coinId)) {
            is Result.Success -> {
                result.payload.collect { coin ->
                    emit(CoinUiState(coin = coin))
                }
            }
            else -> {
                emit(CoinUiState(coinError = true))
            }
        }
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
    val coinError: Boolean = false
)
