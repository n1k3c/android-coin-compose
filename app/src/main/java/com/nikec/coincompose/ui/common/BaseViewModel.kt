package com.nikec.coincompose.ui.common

import androidx.lifecycle.ViewModel
import com.nikec.coincompose.ui.coins.CoinsSideEffect
import com.nikec.coincompose.ui.coins.CoinsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Event, State, SideEffects>(defaultState: State) : ViewModel() {

    internal val _uiState = MutableStateFlow<State>(defaultState)
    val uiState: StateFlow<State> = _uiState

    internal val sideEffectChannel = Channel<SideEffects>(Channel.CONFLATED)
    val sideEffect: Flow<SideEffects> = sideEffectChannel.receiveAsFlow()

    abstract fun processEvent(event: Event)
}
