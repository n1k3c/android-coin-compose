package com.nikec.coincompose.core.di

import android.content.Context
import androidx.room.Room
import com.nikec.coincompose.core.db.*
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
    fun provideCleverestDatabase(@ApplicationContext context: Context, moshi: Moshi): CoinsDatabase {
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
