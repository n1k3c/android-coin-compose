package com.nikec.coincompose.ui.coin

import com.nikec.coincompose.ui.coins.CoinsState

data class CoinState(val showInfoDialog: Boolean = false)

sealed class CoinSideEffect {

}

sealed class CoinEvent {
    object OnBackClicked : CoinEvent()
    object ShowInfoDialog : CoinEvent()
    object HideInfoDialog : CoinEvent()
}
