package com.nikec.coincompose.news.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.nikec.coincompose.core.utils.BaseUseCase
import com.nikec.coincompose.news.data.model.News
import com.nikec.coincompose.news.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchCoinsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseUseCase<Unit, Flow<PagingData<News>>>() {

    override fun execute(parameters: Unit): Flow<PagingData<News>> {
        return newsRepository.fetchNews().map {  pagingData ->
            pagingData.map { news ->
                val newsImage = withContext(Dispatchers.IO) {
                    newsRepository.fetchNewsImage(news)
                }
                val newsWithImage = news.copy(image = newsImage)
                newsWithImage
            }
        }
    }
}
