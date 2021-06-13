package com.nikec.coincompose.view.coins

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikec.coincompose.core.navigation.CoinsDirections
import com.nikec.coincompose.core.navigation.NavigationManager
import com.nikec.coincompose.data.repository.CoinsRepository
import com.nikec.coincompose.domain.coins.GetCoinsUseCase

import com.nikec.coincompose.view.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val coinsRepository: CoinsRepository
) : BaseViewModel<CoinsEvent, CoinsState, CoinsSideEffect>(CoinsState(isLoading = true)) {

    //val coins = coinsRepository.fetchCoins().cachedIn(viewModelScope)

    init {
//        coinsRepository.fetchCoins().cachedIn(viewModelScope).map {
//            setState { copy(coinListPaginated = it) }
//        }

        setState {
            copy(coinListPaginated = getCoinsUseCase.invoke(Unit).cachedIn(viewModelScope))
        }
//        viewModelScope.launch {
////            when (val result = getCoinsUseCase.invoke(Unit)) {
////                is Result.Success -> d { result.payload.toString() }
////                is Result.HttpError -> e { "HttpError = " + result.exception }
////                Result.NetworkError -> e { "NetworkError" }
////                is Result.UnknownError -> e { "UnknownError = " + result.throwable }
////            }
//        }
    }

    override fun processEvent(event: CoinsEvent) {
        when (event) {
            CoinsEvent.OnCoinClicked -> onCoinClicked()
            CoinsEvent.OnShowContentClicked -> onShowContentClicked()
            CoinsEvent.OnShowContentClicked2 -> onShowToastClicked2()
            CoinsEvent.OnShowToastClicked -> onShowToastClicked()
        }
    }

    private fun onCoinClicked() {
        navigationManager.navigate(CoinsDirections.coin)
    }

    private fun onShowContentClicked() {
        setState { copy(isLoading = false, coinList = listOf("1", "2", "3")) }
    }

    private fun onShowContentClicked2() {
        setState { copy(coinList = listOf("3", "5", "6")) }
    }

    private fun onShowToastClicked() {
        setSideEffect { CoinsSideEffect.ShowToast }
    }

    private fun onShowToastClicked2() {
        setSideEffect { CoinsSideEffect.ShowToast }
    }
}
