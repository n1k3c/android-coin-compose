package com.nikec.coincompose.di

import android.content.Context
import androidx.room.Room
import com.nikec.coincompose.core.db.CoinsDao
import com.nikec.coincompose.core.db.CoinsDatabase
import com.nikec.coincompose.core.db.CoinsRemoteKeysDao
import com.nikec.coincompose.core.db.DATABASE_NAME
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
    fun provideCleverestDatabase(@ApplicationContext context: Context): CoinsDatabase {
        return Room.databaseBuilder(
            context, CoinsDatabase::class.java,
            DATABASE_NAME
        )
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
