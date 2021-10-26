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

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nikec.coincompose.core.ui.atoms.AppendLoadingIndicator
import com.nikec.coincompose.core.ui.atoms.ConnectivityStatus
import com.nikec.coincompose.core.ui.atoms.ErrorStatus
import com.nikec.coincompose.core.ui.atoms.SwipeToRefreshIndicator
import com.nikec.coincompose.core.ui.theme.divider
import com.nikec.coincompose.data.model.News
import com.nikec.coincompose.news.R
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun NewsContent(
    newsList: LazyPagingItems<News>,
    onRefresh: () -> Unit,
    onNewsClicked: (News) -> Unit,
    onRetryClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ConnectivityStatus()
        NewsList(
            newsList = newsList,
            onRefresh = onRefresh,
            onNewsClicked = onNewsClicked,
            onRetryClicked = onRetryClicked
        )
    }
}

@Composable
private fun NewsList(
    newsList: LazyPagingItems<News>,
    onRefresh: () -> Unit,
    onNewsClicked: (News) -> Unit,
    onRetryClicked: () -> Unit
) {
    Box {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(isRefreshing = newsList.loadState.refresh is LoadState.Loading),
            onRefresh = { onRefresh() },
            indicator = { state, trigger ->
                SwipeToRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger
                )
            }) {
            LazyColumn(contentPadding = PaddingValues(bottom = 12.dp)) {
                items(newsList) { news ->
                    news?.let {
                        NewsItem(
                            news = news,
                            onNewsClicked = onNewsClicked
                        )
                        Divider(color = MaterialTheme.colors.divider)
                    }
                }

                newsList.apply {
                    if (loadState.append is LoadState.Loading) {
                        item {
                            AppendLoadingIndicator()
                        }
                    }
                    if (loadState.append is LoadState.Error) {
                        item {
                            ErrorStatus(throwable = (loadState.append as LoadState.Error).error) {
                                onRetryClicked()
                            }
                        }
                    }
                }
            }
        }

        if (newsList.loadState.refresh is LoadState.Error && newsList.snapshot().isEmpty()) {
            ErrorStatus(throwable = (newsList.loadState.refresh as LoadState.Error).error) {
                onRetryClicked()
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun NewsItem(news: News, onNewsClicked: (News) -> Unit) {
    val timePassedFromPublishedAt = if (news.publishedAt != null) {
        val millis =
            ZonedDateTime.of(news.publishedAt, ZoneId.systemDefault()).toInstant().toEpochMilli()
        DateUtils.getRelativeTimeSpanString(millis).toString()
    } else {
        stringResource(id = R.string.not_available)
    }

    val errorDrawable = if (isSystemInDarkTheme()) {
        R.drawable.ic_error_image_white
    } else {
        R.drawable.ic_error_image
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .clickable { onNewsClicked(news) }) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .padding(start = 8.dp, top = 12.dp, bottom = 12.dp),
            painter = rememberImagePainter(
                data = news.image,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_image)
                    error(errorDrawable)
                    fallback(errorDrawable)
                    transformations(RoundedCornersTransformation(radius = 35f))
                }
            ),
            contentDescription = news.title
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 10.dp, bottom = 12.dp, end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.body2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Column {
                Text(
                    text = news.source.title,
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp)
                )
                Text(
                    text = timePassedFromPublishedAt,
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp)
                )
            }
        }
    }
}
