package com.nikec.coincompose.news.ui.list

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.nikec.coincompose.news.R
import com.nikec.coincompose.news.data.model.News
import java.time.*

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

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(8.dp)
        .clickable { onNewsClicked(news) }) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = rememberImagePainter(
                data = news.image,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_image)
                    error(R.drawable.ic_error_image)
                    fallback(R.drawable.ic_error_image)
                    transformations(RoundedCornersTransformation(radius = 35f))
                }
            ),
            contentDescription = news.title
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 2.dp, bottom = 2.dp),
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
