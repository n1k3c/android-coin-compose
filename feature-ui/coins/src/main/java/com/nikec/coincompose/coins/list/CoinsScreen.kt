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

package com.nikec.coincompose.coins.list

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nikec.coincompose.core.ui.extensions.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect

@Composable
fun CoinsScreen(viewModel: CoinsViewModel) {
    val coinsList = viewModel.paginatedCoins.collectAsLazyPagingItems()
    val coinListEvents = rememberFlowWithLifecycle(flow = viewModel.coinListEvents)
    val currency =
        rememberFlowWithLifecycle(flow = viewModel.currency).collectAsState(initial = null).value
    val scrollState = rememberScrollState()
    val listState = rememberLazyListState()

    LaunchedEffect(coinListEvents) {
        coinListEvents.collect {
            when (it) {
                CoinListEvent.Refresh -> coinsList.refresh()
                CoinListEvent.Retry -> coinsList.retry()
                CoinListEvent.ScrollToTop -> listState.animateScrollToItem(0)
            }
        }
    }

    Scaffold {
        CoinsContent(
            coinsList = coinsList,
            onCoinClicked = viewModel::onCoinClicked,
            onScrollToTopClicked = viewModel::onScrollToTopClicked,
            onRefresh = viewModel::onRefresh,
            onRetryClicked = viewModel::onRetryClicked,
            currency = currency,
            scrollState = scrollState,
            listState = listState
        )
    }
}
