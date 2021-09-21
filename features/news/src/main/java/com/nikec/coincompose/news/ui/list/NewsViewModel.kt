package com.nikec.coincompose.news.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nikec.coincompose.news.domain.FetchCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    fetchCoinsUseCase: FetchCoinsUseCase
) : ViewModel() {

    val paginatedNews = fetchCoinsUseCase.invoke(Unit).cachedIn(viewModelScope)
}
