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

import com.nikec.coincompose.data.BuildConfig
import com.nikec.coincompose.data.api.CoinsService
import com.nikec.coincompose.data.api.DateAdapter
import com.nikec.coincompose.data.api.DatePattern
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinsApiServiceModule {

    @Provides
    @Singleton
    fun provideCoinsService(moshi: Moshi): CoinsService {
        val okHttpClient = createOkHttpClient()
        val retrofit = createRetrofit(okHttpClient, moshi)
        return retrofit.create(CoinsService::class.java)
    }

    @Provides
    @Singleton
    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    @Provides
    @Singleton
    fun createRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(attachMoshiAdapters(moshi)))
            .client(okHttpClient)
            .baseUrl(BuildConfig.COINS_BASE_URL)
            .build()
    }

    private fun attachMoshiAdapters(moshi: Moshi): Moshi {
        return moshi.newBuilder().add(DateAdapter(datePattern = DatePattern.COINS)).build()
    }
}
