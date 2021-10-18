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
