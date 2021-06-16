package com.nikec.coincompose.view.coin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber.d
import com.nikec.coincompose.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val earthquakeId = savedStateHandle.get<String>("coinId")

    init {
        d { "coinId = " + earthquakeId.toString() }
    }

    fun onBackClicked() {
        navigationManager.navigateBack()
    }
}
