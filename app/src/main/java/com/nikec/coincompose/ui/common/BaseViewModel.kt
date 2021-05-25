package com.nikec.coincompose.ui.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Event, State, SideEffects>(defaultState: State) : ViewModel() {

    protected val mutableUiState = MutableStateFlow<State>(defaultState)
    val uiState: StateFlow<State> = mutableUiState

    protected val sideEffectChannel = Channel<SideEffects>(Channel.CONFLATED)
    val sideEffect: Flow<SideEffects> = sideEffectChannel.receiveAsFlow()

    abstract fun processEvent(event: Event)
}
