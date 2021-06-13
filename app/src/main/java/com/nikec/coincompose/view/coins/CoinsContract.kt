package com.nikec.coincompose.view.coins

import androidx.paging.PagingData
import com.nikec.coincompose.data.model.CoinShort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CoinsState(
    val isLoading: Boolean = false,
    val coinList: List<String>? = null,
    val coinListPaginated: Flow<PagingData<CoinShort>> = emptyFlow()
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
