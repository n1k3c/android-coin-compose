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

package com.nikec.coincompose.data.di

import android.content.Context
import androidx.room.Room
import com.nikec.coincompose.data.db.*
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCleverestDatabase(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): CoinsDatabase {
        return Room.databaseBuilder(
            context, CoinsDatabase::class.java,
            DATABASE_NAME
        )
            .addTypeConverter(DatabaseTypeConverter(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinsDao(coinsDatabase: CoinsDatabase): CoinsDao {
        return coinsDatabase.coinsDao()
    }

    @Provides
    @Singleton
    fun provideCoinsRemoteKeysDao(coinsDatabase: CoinsDatabase): CoinsRemoteKeysDao {
        return coinsDatabase.coinsRemoteKeysDao()
    }
}
