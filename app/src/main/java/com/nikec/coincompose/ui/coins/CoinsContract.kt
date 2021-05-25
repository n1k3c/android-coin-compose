package com.nikec.coincompose.ui.coins

sealed class CoinsState {
    object Idle : CoinsState()
    object Loading : CoinsState()
    data class CoinsContent(val coinList: List<String>) : CoinsState()
}

sealed class CoinsSideEffect {
    object ShowToast : CoinsSideEffect()
}

sealed class CoinsEvent {
    object OnCoinClicked : CoinsEvent()
    object OnShowContentClicked : CoinsEvent()
    object OnShowContentClicked2 : CoinsEvent()
    object OnShowToastClicked : CoinsEvent()
}
