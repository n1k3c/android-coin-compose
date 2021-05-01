package com.nikec.coincompose.ui.coins

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import com.nikec.coincompose.navigation.CoinsDirections
import com.nikec.coincompose.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoinsState>(CoinsState.Idle)
    val uiState: StateFlow<CoinsState> = _uiState

    fun processEvent(event: CoinsEvent) = when (event) {
        CoinsEvent.OnCoinClicked -> onCoinClicked()
        CoinsEvent.OnShowContentClicked -> onShowContentClicked()
        CoinsEvent.OnShowToastClicked -> onShowToastClicked()
    }

    private fun onCoinClicked() {
        d { "onCoinClicked" }
        navigationManager.navigate(CoinsDirections.coin)
    }

    private fun onShowContentClicked() {
        _uiState.value = CoinsState.CoinsContent(listOf<String>("1", "2", "3"))
    }

    private fun onShowToastClicked() {
        _uiState.value = CoinsState.ShowToast
    }
}

sealed class CoinsState {
    object Idle : CoinsState()
    object Loading : CoinsState()
    data class CoinsContent(val coinList: List<String>) : CoinsState()
    data class Error(val error: Throwable) : CoinsState()
    object ShowToast : CoinsState()
}