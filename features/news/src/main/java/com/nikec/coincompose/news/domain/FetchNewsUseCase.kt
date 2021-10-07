package com.nikec.coincompose.news.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.core.utils.PagingInteractor
import com.nikec.coincompose.news.data.api.NewsService
import com.nikec.coincompose.news.data.model.News
import com.nikec.coincompose.news.data.paging.NewsPagingSource
import com.nikec.coincompose.news.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val newsRepository: NewsRepository,
    private val newsService: NewsService
) : PagingInteractor<FetchNewsUseCase.Params, News>() {

    override fun createObservable(params: Params): Flow<PagingData<News>> {
        return Pager(
            config = params.pagingConfig,
            pagingSourceFactory = {
                NewsPagingSource(
                    newsService = newsService
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { news ->
                val newsImage = withContext(coroutineContextProvider.io) {
                    newsRepository.fetchNewsImage(news)
                }
                news.copy(image = newsImage)
            }
        }.flowOn(coroutineContextProvider.io)
    }

    data class Params(override val pagingConfig: PagingConfig) : PagingInteractor.Params<News>
}
