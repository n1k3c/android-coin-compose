package com.nikec.coincompose.view.coins

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.data.model.CoinShort
import com.nikec.coincompose.domain.coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    private val getCoinsUseCase: GetCoinsUseCase,
) : ViewModel() {

    val paginatedCoins: Flow<PagingData<CoinShort>> =
        getCoinsUseCase.invoke(Unit).cachedIn(viewModelScope)

    fun onCoinClicked(coin: CoinShort) {
        navigationManager.navigate(CoinsDirections.coin(coin.id))
    }
}
