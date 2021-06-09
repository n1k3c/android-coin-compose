package com.nikec.coincompose.view.coin

import androidx.lifecycle.SavedStateHandle
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.view.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : BaseViewModel<CoinEvent, CoinState, CoinSideEffect>(CoinState()) {

    override fun processEvent(event: CoinEvent) {
        when (event) {
            CoinEvent.OnBackClicked -> navigateBack()
            CoinEvent.ShowInfoDialog -> showInfoDialog()
            CoinEvent.HideInfoDialog -> hideInfoDialog()
        }
    }

    private fun navigateBack() {
        navigationManager.navigateBack()
    }

    private fun showInfoDialog() {
        setState { copy(showInfoDialog = true) }
    }

    private fun hideInfoDialog() {
        setState { copy(showInfoDialog = false) }
    }
}
