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

package com.nikec.coincompose.news.list

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.paging.compose.collectAsLazyPagingItems
import com.nikec.coincompose.core.ui.extensions.rememberFlowWithLifecycle
import com.nikec.coincompose.data.model.News
import com.nikec.coincompose.news.BuildConfig
import kotlinx.coroutines.flow.collect

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val newsList = viewModel.paginatedNews.collectAsLazyPagingItems()
    val newsListEvents = rememberFlowWithLifecycle(flow = viewModel.newsListEvents)
    val context = LocalContext.current

    LaunchedEffect(newsListEvents) {
        newsListEvents.collect {
            when (it) {
                NewsListEvent.Refresh -> newsList.refresh()
                NewsListEvent.Retry -> newsList.retry()
                is NewsListEvent.OpenDetails -> openNewsDetails(context = context, news = it.news)
            }
        }
    }

    Scaffold {
        NewsContent(
            newsList = newsList,
            onRefresh = viewModel::onRefresh,
            onNewsClicked = viewModel::onNewsClicked,
            onRetryClicked = viewModel::onRetryClicked
        )
    }
}

private fun openNewsDetails(context: Context, news: News) {
    val uri = "${BuildConfig.NEWS_BASE_URL}news/${news.id}/click/".toUri()
    val customTabBuilder = CustomTabsIntent.Builder().apply {
        setShowTitle(true)
    }
    val customTabsIntent = customTabBuilder.build()
    customTabsIntent.launchUrl(context, uri)
}
