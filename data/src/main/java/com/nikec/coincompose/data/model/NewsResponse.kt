/*
 * Developed by n1k3c (Nikola Curilović)  2021
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nikec.coincompose.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<News>
)

data class News(
    @Json(name = "created_at")
    val createdAt: LocalDateTime?,
    val currencies: List<CryptoCurrency>?,
    val domain: String,
    val id: Int,
    @Json(name = "published_at")
    val publishedAt: LocalDateTime?,
    val source: Source,
    val title: String,
    val url: String,
    val image: String?
)

data class CryptoCurrency(
    val code: String,
    val title: String,
    val url: String
)

data class Source(
    val region: String,
    val title: String
)
