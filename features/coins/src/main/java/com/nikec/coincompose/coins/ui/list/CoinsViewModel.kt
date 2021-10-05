package com.nikec.coincompose.coins.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nikec.coincompose.coins.domain.FetchCoinsUseCase
import com.nikec.coincompose.coins.navigation.CoinsDirections
import com.nikec.coincompose.core.data.model.Coin
import com.nikec.coincompose.core.data.model.Currency
import com.nikec.coincompose.core.domain.GetCurrencyUseCase
import com.nikec.coincompose.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    fetchCoinsUseCase: FetchCoinsUseCase,
    getCurrencyUseCase: GetCurrencyUseCase
) : ViewModel() {

    private val coinListEventsChannel = Channel<CoinListEvent>(Channel.CONFLATED)
    val coinListEvents: Flow<CoinListEvent> = coinListEventsChannel.receiveAsFlow()

    init {
        getCurrencyUseCase.invoke(Unit)
    }

    val currency: StateFlow<Currency?> = getCurrencyUseCase.flow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val paginatedCoins: Flow<PagingData<Coin>> = currency.filterNotNull().flatMapLatest { currency ->
        fetchCoinsUseCase.execute(currency)
    }.cachedIn(viewModelScope)

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
