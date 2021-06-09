package com.nikec.coincompose.view.coins

data class CoinsState(
    val isLoading: Boolean = false,
    val coinList: List<String>? = null
)

sealed class CoinsSideEffect {
    object ShowToast : CoinsSideEffect()
}

sealed class CoinsEvent {
    object OnCoinClicked : CoinsEvent()
    object OnShowContentClicked : CoinsEvent()
    object OnShowContentClicked2 : CoinsEvent()
    object OnShowToastClicked : CoinsEvent()
}
