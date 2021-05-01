package com.nikec.coincompose.ui.coin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

}