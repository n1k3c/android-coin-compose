package com.nikec.coincompose.news.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.news.data.model.News
import com.nikec.coincompose.news.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val newsRepository: NewsRepository
) {

    fun execute(): Flow<PagingData<News>> {
        return newsRepository.fetchNews().map { pagingData ->
            pagingData.map { news ->
                val newsImage = newsRepository.fetchNewsImage(news)
                news.copy(image = newsImage)
            }
        }.flowOn(coroutineContextProvider.io)
    }
}
