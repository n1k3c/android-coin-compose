package com.nikec.coincompose.ui.coins

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.navigation.CoinsDirections
import com.nikec.coincompose.navigation.NavigationManager
import com.nikec.coincompose.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : BaseViewModel<CoinsEvent, CoinsState, CoinsSideEffect>(CoinsState.Idle) {

    override fun processEvent(event: CoinsEvent) {
        i { "proces event -> " + event.toString() }
        when (event) {
            CoinsEvent.OnCoinClicked -> onCoinClicked()
            CoinsEvent.OnShowContentClicked -> onShowContentClicked()
            CoinsEvent.OnShowContentClicked2 -> onShowToastClicked2()
            CoinsEvent.OnShowToastClicked -> onShowToastClicked()
        }
    }

    private fun onCoinClicked() {
        navigationManager.navigate(CoinsDirections.coin)
    }

    private fun onShowContentClicked() {
        _uiState.value = CoinsState.CoinsContent(listOf<String>("1", "2", "3"))
    }

    private fun onShowContentClicked2() {
        _uiState.value = CoinsState.CoinsContent(listOf<String>("3", "5", "6"))
    }

    private fun onShowToastClicked() {
        viewModelScope.launch {
            sideEffectChannel.send(CoinsSideEffect.ShowToast)
        }
    }

    private fun onShowToastClicked2() {
        viewModelScope.launch {
            sideEffectChannel.send(CoinsSideEffect.ShowToast)
        }
    }
}

sealed class CoinsState {
    object Idle : CoinsState()
    object Loading : CoinsState()
    data class CoinsContent(val coinList: List<String>) : CoinsState()
}

sealed class CoinsSideEffect {
    object ShowToast : CoinsSideEffect()
}

sealed class CoinsEvent {
    object OnCoinClicked : CoinsEvent()
    object OnShowContentClicked : CoinsEvent()
    object OnShowContentClicked2 : CoinsEvent()
    object OnShowToastClicked : CoinsEvent()
}
