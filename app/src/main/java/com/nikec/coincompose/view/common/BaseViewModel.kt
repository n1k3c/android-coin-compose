package com.nikec.coincompose.view.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event, State, SideEffects>(defaultState: State) : ViewModel() {

    private val mutableUiState = MutableStateFlow(defaultState)
    val uiState = mutableUiState.asStateFlow()

    private val sideEffectChannel = Channel<SideEffects>(Channel.CONFLATED)
    val sideEffect = sideEffectChannel.receiveAsFlow()

    abstract fun processEvent(event: Event)

    protected fun setState(reduce: State.() -> State) {
        val newState = mutableUiState.value.reduce()
        mutableUiState.value = newState
    }

    protected fun setSideEffect(builder: () -> SideEffects) {
        val effectValue = builder()
        viewModelScope.launch { sideEffectChannel.send(effectValue) }
    }
}
