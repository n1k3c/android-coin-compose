package com.nikec.coincompose.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.nikec.coincompose.core.Settings
import com.nikec.coincompose.core.data.datastore.SettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.settingsDataStore: DataStore<Settings> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object ProtoStoreModule {

    @Singleton
    @Provides
    fun providesSettingsDataStore(@ApplicationContext context: Context): DataStore<Settings> =
        context.settingsDataStore
}
