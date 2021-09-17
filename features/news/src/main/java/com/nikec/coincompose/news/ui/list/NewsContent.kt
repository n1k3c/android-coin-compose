package com.nikec.coincompose.news.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "News list")
    }
}
