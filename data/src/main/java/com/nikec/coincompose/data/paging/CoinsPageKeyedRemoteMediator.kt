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

package com.nikec.coincompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.ajalt.timberkt.Timber.e
import com.nikec.coincompose.core.utils.Result
import com.nikec.coincompose.core.utils.safeApiCall
import com.nikec.coincompose.data.api.CoinsService
import com.nikec.coincompose.data.db.CoinsDatabase
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.model.CoinRemoteKeys
import com.nikec.coincompose.data.model.Currency
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class CoinsPageKeyedRemoteMediator(
    private val db: CoinsDatabase,
    private val coinsService: CoinsService,
    private val currency: Currency,
    private val maxPages: Int
) : RemoteMediator<Int, Coin>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Coin>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: throw InvalidObjectException("Result is empty")
                remoteKeys.nextKey ?: return MediatorResult.Success(true)
            }
        }

        return when (val result = safeApiCall {
            coinsService.fetchCoins(
                page = page,
                perPage = state.config.pageSize,
                currency = currency.key
            )
        }) {
            is Result.Success -> {
                val endOfPaginationReached = page == maxPages

                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.coinsRemoteKeysDao().deleteAll()
                        db.coinsDao().deleteAll()
                    }

                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val keys = result.payload.map {
                        CoinRemoteKeys(coinId = it.id, prevKey = prevKey, nextKey = nextKey)
                    }

                    db.coinsRemoteKeysDao().insertAll(keys)
                    db.coinsDao().insertAll(result.payload)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
            is Result.HttpError -> {
                e { "Http error -> " + result.exception.toString() }
                MediatorResult.Error(result.exception)
            }
            is Result.NetworkError -> {
                e { "Network error" }
                MediatorResult.Error(result.noInternetThrowable)
            }
            is Result.UnknownError -> {
                e { "Unknown error -> " + result.throwable.toString() }
                MediatorResult.Error(result.throwable)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Coin>): CoinRemoteKeys? {
        return if (state.isEmpty()) {
            db.withTransaction { db.coinsRemoteKeysDao().getAll().lastOrNull() }
        } else {
            state.lastItemOrNull()?.let { coin ->
                db.withTransaction { db.coinsRemoteKeysDao().getRemoteKeysByCoinId(coin.id) }
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Coin>
    ): CoinRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.withTransaction { db.coinsRemoteKeysDao().getRemoteKeysByCoinId(id) }
            }
        }
    }
}
