package com.baratali.cnote.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.work.WorkManager
import com.baratali.cnote.feature_task.domain.model.LocalDateTimeAdapter
import com.baratali.cnote.settings.data.data_store.DataStoreRepositoryImpl
import com.baratali.cnote.settings.data.data_store.dto.Settings
import com.baratali.cnote.settings.data.data_store.dto.SettingsSerializer
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import com.baratali.cnote.settings.util.Constant.DATA_STORE_FILE_NAME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDataStoreForSensitiveData(
        @ApplicationContext context: Context,
        serializer: SettingsSerializer
    ): DataStore<Settings> {
        return DataStoreFactory.create(
            serializer = serializer,
            produceFile = { context.dataStoreFile(DATA_STORE_FILE_NAME) }
        )
    }

    @Provides
    @Singleton
    fun provideTimeSettingsSerializer() = SettingsSerializer

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        dataStore: DataStore<Settings>
    ): DataStoreRepository = DataStoreRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()
}