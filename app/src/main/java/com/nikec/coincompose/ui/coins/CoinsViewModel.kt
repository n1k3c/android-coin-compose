package com.nikec.coincompose.ui.coins

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.navigation.CoinsDirections
import com.nikec.coincompose.navigation.NavigationManager
import com.nikec.coincompose.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : BaseViewModel<CoinsEvent, CoinsState, CoinsSideEffect>(CoinsState(isLoading = true)) {

    override fun processEvent(event: CoinsEvent) {
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
        setState { copy(isLoading = false, coinList = listOf("1", "2", "3")) }
    }

    private fun onShowContentClicked2() {
        setState { copy(coinList = listOf("3", "5", "6")) }
    }

    private fun onShowToastClicked() {
        setSideEffect { CoinsSideEffect.ShowToast }
    }

    private fun onShowToastClicked2() {
        setSideEffect { CoinsSideEffect.ShowToast }
    }
}
