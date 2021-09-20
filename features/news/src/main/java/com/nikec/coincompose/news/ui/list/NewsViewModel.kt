package com.nikec.coincompose.news.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.news.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = newsRepository.fetchNews()) {
                is Result.Success -> {
                    i { "result -> " + result.payload.results.toString() }
                }
                is Result.HttpError -> {
                    e { "Http error -> " + result.exception.toString() }
                }
                is Result.NetworkError -> {
                    e { "Network error" }
                }
                is Result.UnknownError -> {
                    e { "Unknown error -> " + result.throwable.toString() }
                }
            }
        }
    }
}
