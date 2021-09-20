package com.nikec.coincompose.news.data.api

import com.nikec.coincompose.news.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("api/v1/posts/")
    suspend fun fetchNews(
        @Query("page") page: Int = 1
    ): NewsResponse
}
