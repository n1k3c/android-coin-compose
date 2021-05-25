package com.nikec.coincompose.ui.coin

sealed class CoinState {
    object Idle : CoinState()
}

sealed class CoinSideEffect {

}

sealed class CoinEvent {
    object OnBackClicked : CoinEvent()
}
