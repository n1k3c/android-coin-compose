package com.nikec.coincompose.data.repository

import com.nikec.coincompose.data.model.News
import org.jsoup.Jsoup
import javax.inject.Inject

interface NewsRepository {
    suspend fun fetchNewsImage(news: News): String?
}

class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    override suspend fun fetchNewsImage(news: News): String? {
        val document = runCatching { Jsoup.connect(news.url).get() }.getOrNull()
        val metaTags = document?.getElementsByTag("meta")

        return metaTags?.firstOrNull { element -> element.attr("property") == "og:image" }
            ?.attr("content")
    }
}
