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
