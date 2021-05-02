package com.nikec.coincompose.ui.coins

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.navigation.CoinsDirections
import com.nikec.coincompose.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoinsState>(CoinsState(isIdle = true))
    val uiState: StateFlow<CoinsState> = _uiState

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow: Flow<Event> = eventChannel.receiveAsFlow()

    fun processEvent(event: CoinsEvent) {
        i { "proces event -> " + event.toString() }
        when (event) {
            CoinsEvent.OnCoinClicked -> onCoinClicked()
            CoinsEvent.OnShowContentClicked -> onShowContentClicked()
            CoinsEvent.OnShowContentClicked2 -> onShowContentClicked2()
            CoinsEvent.OnShowToastClicked -> onShowToastClicked()
        }
    }

    private fun onCoinClicked() {
        navigationManager.navigate(CoinsDirections.coin)
    }

    private fun onShowContentClicked() {
        _uiState.value = CoinsState(coinContent = listOf<String>("1", "2", "3"))
    }

    private fun onShowContentClicked2() {
        _uiState.value = CoinsState(coinContent = listOf<String>("3", "5", "6"))
    }

    private fun onShowToastClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.ShowToast)
        }
    }
}


data class CoinsState(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val coinContent: List<String> = emptyList(),
)

//sealed class CoinsState {
//    object Idle : CoinsState()
//    object Loading : CoinsState()
//    data class CoinsContent(val coinList: List<String>) : CoinsState()
//    data class Error(val error: Throwable) : CoinsState()
//    object ShowToast : CoinsState()
//}

sealed class Event {
    object ShowToast : Event()
}