package com.nikec.coincompose.news.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.github.ajalt.timberkt.i
import com.nikec.coincompose.news.data.model.News

@Composable
fun NewsContent(coinsList: LazyPagingItems<News>) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(coinsList) {
                Text(text = it?.title.toString())
                i { "news image => " + it?.image }
            }
        }
    }
}
