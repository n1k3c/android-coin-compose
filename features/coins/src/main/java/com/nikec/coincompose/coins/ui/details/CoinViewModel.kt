package com.nikec.coincompose.coins.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber.d
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.core.navigation.directions.CoinsDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val coinId = savedStateHandle.get<String>(CoinsDirections.COIN_ID)

    init {
        d { "coinId = " + coinId.toString() }
    }

    fun onBackClicked() {
        navigationManager.navigateBack()
    }
}
