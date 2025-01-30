package com.example.cnote.settings.data.data_store

import androidx.datastore.core.DataStore
import com.example.cnote.settings.data.data_store.dto.Settings
import com.example.cnote.settings.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Settings>
) : DataStoreRepository {

    override fun getSettings(): Flow<Settings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(Settings())
            } else {
                throw exception
            }
        }

    override suspend fun updateDarkMode(isDark: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(isDark = isDark)
        }
    }

    override suspend fun updateNotificationsEnabled(isEnabled: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(notificationEnabled = isEnabled)
        }
    }
}