package com.nikec.coincompose.news.ui.list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun NewsScreen(viewModel: NewsViewModel) {

    Scaffold {
        NewsContent()
    }
}
