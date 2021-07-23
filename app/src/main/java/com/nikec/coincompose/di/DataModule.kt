package com.nikec.coincompose.core.di

import com.nikec.coincompose.coins.data.repository.CoinsRepository
import com.nikec.coincompose.coins.data.repository.CoinsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCoinsRepository(coinsRepositoryImpl: CoinsRepositoryImpl): CoinsRepository
}
