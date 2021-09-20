package com.nikec.coincompose.news.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<News>
)

data class News(
    @Json(name = "created_at")
    val createdAt: String,
    val currencies: List<Currency>?,
    val domain: String,
    val id: Int,
    @Json(name = "published_at")
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String
)

data class Currency(
    val code: String,
    val title: String,
    val url: String
)

data class Source(
    val region: String,
    val title: String
)
