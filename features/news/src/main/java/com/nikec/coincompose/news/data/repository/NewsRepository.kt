package com.nikec.coincompose.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nikec.coincompose.core.utils.CoroutineContextProvider
import com.nikec.coincompose.news.data.api.NewsService
import com.nikec.coincompose.news.data.model.News
import com.nikec.coincompose.news.data.paging.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import org.jsoup.Jsoup
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val INITIAL_LOAD_SIZE = PAGE_SIZE

interface NewsRepository {
    fun fetchNews(): Flow<PagingData<News>>
    suspend fun fetchNewsImage(news: News): String?
}

class NewsRepositoryImpl @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider(),
    private val newsService: NewsService,
) : NewsRepository {

    override fun fetchNews(): Flow<PagingData<News>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                enablePlaceholders = true
            ),
            // Can not inject NewsPagingSource because we need to always create
            // a new object on refresh
            pagingSourceFactory = {
                NewsPagingSource(
                    coroutineContextProvider = coroutineContextProvider,
                    newsService = newsService
                )
            }
        ).flow
    }

    override suspend fun fetchNewsImage(news: News): String? {
        val document = runCatching { Jsoup.connect(news.url).get() }.getOrNull()
        val metaTags = document?.getElementsByTag("meta")

        return metaTags?.firstOrNull { element -> element.attr("property") == "og:image" }
            ?.attr("content")
    }
}
