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

package com.nikec.coincompose.data.repository

import androidx.paging.PagingSource
import com.nikec.coincompose.data.db.CoinsDatabase
import com.nikec.coincompose.data.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CoinsRepository {
    fun getCoins(): PagingSource<Int, Coin>
    fun getCoin(coinId: String): Flow<Coin?>
}

class CoinsRepositoryImpl @Inject constructor(
    private val db: CoinsDatabase
) : CoinsRepository {

    override fun getCoins(): PagingSource<Int, Coin> = db.coinsDao().observeCoinsPaginated()

    override fun getCoin(coinId: String): Flow<Coin?> = db.coinsDao().getCoin(coinId)
}
