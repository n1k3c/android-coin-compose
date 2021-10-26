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

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nikec.coincompose.data.model.Coin
import com.nikec.coincompose.data.model.CoinRemoteKeys

const val DATABASE_NAME = "coins.db"

@Database(
    entities = [Coin::class, CoinRemoteKeys::class],
    version = 1
)
@TypeConverters(DatabaseTypeConverter::class)
abstract class CoinsDatabase : RoomDatabase() {

    companion object {
        const val COINS_TABLE = "coins"

        const val COINS_REMOTE_KEYS_TABLE = "coins_remote_keys"
    }

    abstract fun coinsDao(): CoinsDao

    abstract fun coinsRemoteKeysDao(): CoinsRemoteKeysDao
}
