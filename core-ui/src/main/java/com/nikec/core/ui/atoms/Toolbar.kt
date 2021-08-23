package com.nikec.core.ui.atoms

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nikec.core.ui.R

@Composable
fun ToolbarWithBack(title: String, onBackClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(
                onClick = { onBackClicked.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
    )
}
