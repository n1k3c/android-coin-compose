package com.nikec.coincompose.news.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.news.data.model.News
import com.nikec.coincompose.news.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchCoinsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun execute(): Flow<PagingData<News>> {
        return newsRepository.fetchNews().map { pagingData ->
            pagingData.map { news ->
                val newsImage = withContext(coroutineContextProvider.io) {
                    newsRepository.fetchNewsImage(news)
                }
                news.copy(image = newsImage)
            }
        }
    }
}
