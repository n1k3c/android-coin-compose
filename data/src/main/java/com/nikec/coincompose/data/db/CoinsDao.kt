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

package com.nikec.coincompose.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikec.coincompose.data.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Query("SELECT * FROM ${CoinsDatabase.COINS_TABLE}")
    fun observeCoinsPaginated(): PagingSource<Int, Coin>

    @Query("DELETE FROM ${CoinsDatabase.COINS_TABLE}")
    fun deleteAll(): Int

    @Query("SELECT * FROM ${CoinsDatabase.COINS_TABLE} WHERE id = :coinId")
    fun getCoin(coinId: String): Flow<Coin?>
}
