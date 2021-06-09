package com.nikec.coincompose.view.coin

data class CoinState(val showInfoDialog: Boolean = false)

sealed class CoinSideEffect {

}

sealed class CoinEvent {
    object OnBackClicked : CoinEvent()
    object ShowInfoDialog : CoinEvent()
    object HideInfoDialog : CoinEvent()
}
