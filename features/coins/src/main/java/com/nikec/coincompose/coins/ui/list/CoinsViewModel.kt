package com.nikec.coincompose.coins.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nikec.coincompose.coins.domain.FetchCoinsUseCase
import com.nikec.coincompose.coins.navigation.CoinsDirections
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    fetchCoinsUseCase: FetchCoinsUseCase
) : ViewModel() {

    private val coinListEventsChannel = Channel<CoinListEvent>(Channel.CONFLATED)
    val coinListEvents: Flow<CoinListEvent> = coinListEventsChannel.receiveAsFlow()

    val paginatedCoins: Flow<PagingData<Coin>> =
        fetchCoinsUseCase.execute().cachedIn(viewModelScope)

    fun onCoinClicked(coin: Coin) {
        navigationManager.navigate(CoinsDirections.coinDetails(coin.id))
    }

    fun onScrollToTopClicked() {
        coinListEventsChannel.trySend(CoinListEvent.ScrollToTop)
    }

    fun onRefresh() {
        coinListEventsChannel.trySend(CoinListEvent.Refresh)
    }

    fun onRetryClicked() {
        coinListEventsChannel.trySend(CoinListEvent.Retry)
    }
}

sealed class CoinListEvent {
    object Refresh : CoinListEvent()
    object ScrollToTop : CoinListEvent()
    object Retry : CoinListEvent()
}
