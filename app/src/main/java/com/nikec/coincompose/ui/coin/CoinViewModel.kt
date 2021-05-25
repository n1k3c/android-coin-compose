package com.nikec.coincompose.ui.coin

import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.navigation.NavigationManager
import com.nikec.coincompose.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : BaseViewModel<CoinEvent, CoinState, CoinSideEffect>(CoinState.Idle) {

    override fun processEvent(event: CoinEvent) {
        i { "process event -> $event" }
        when (event) {
            CoinEvent.OnBackClicked -> navigateBack()
        }
    }

    private fun navigateBack() {
        navigationManager.navigateBack()
    }
}
