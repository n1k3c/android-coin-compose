package com.nikec.coincompose.news.data.api

import com.nikec.coincompose.news.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NewsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = chain.request().url.newBuilder()
            .addQueryParameter("auth_token", BuildConfig.NEWS_API_KEY)
            .addQueryParameter("public", "true")
            .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}
