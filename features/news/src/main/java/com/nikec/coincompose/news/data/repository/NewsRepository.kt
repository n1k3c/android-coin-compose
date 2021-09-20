package com.nikec.coincompose.news.data.repository

import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import com.nikec.coincompose.news.data.api.NewsService
import com.nikec.coincompose.news.data.model.NewsResponse
import javax.inject.Inject


interface NewsRepository {
    suspend fun fetchNews(): Result<NewsResponse>
}

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService
) : NewsRepository {

    override suspend fun fetchNews(): Result<NewsResponse> =
        safeApiCall { newsService.fetchNews() }
}
