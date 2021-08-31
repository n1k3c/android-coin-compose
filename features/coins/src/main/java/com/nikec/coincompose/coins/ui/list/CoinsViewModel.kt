package com.nikec.coincompose.coins.ui.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nikec.coincompose.coins.domain.GetCoinsUseCase
import com.nikec.coincompose.core.model.Coin
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.core.navigation.directions.CoinsDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val coinListActionChannel = Channel<CoinListAction>(Channel.CONFLATED)
    val coinListAction: Flow<CoinListAction> = coinListActionChannel.receiveAsFlow()

    val paginatedCoins: Flow<PagingData<Coin>> =
        getCoinsUseCase.invoke(Unit).cachedIn(viewModelScope)

    fun onCoinClicked(coin: Coin) {
        navigationManager.navigate(CoinsDirections.coin(coin.id))
    }

    fun onScrollToTopClicked() {
        coinListActionChannel.trySend(CoinListAction.ScrollToTop)
    }

    fun onRefresh() {
        coinListActionChannel.trySend(CoinListAction.Refresh)
    }

    fun onRetryClicked() {
        coinListActionChannel.trySend(CoinListAction.Retry)
    }
}

sealed class CoinListAction {
    object Refresh : CoinListAction()
    object ScrollToTop : CoinListAction()
    object Retry : CoinListAction()
}