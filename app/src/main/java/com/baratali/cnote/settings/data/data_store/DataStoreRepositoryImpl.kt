package com.baratali.cnote.settings.data.data_store

import androidx.datastore.core.DataStore
import com.baratali.cnote.feature_note.util.PasswordUtils.hashPassword
import com.baratali.cnote.settings.data.data_store.dto.DarkMode
import com.baratali.cnote.settings.data.data_store.dto.Settings
import com.baratali.cnote.settings.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
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

    override suspend fun updateDarkMode(darkMode: DarkMode) {
        dataStore.updateData { it.copy(darkMode = darkMode) }
    }

    override suspend fun updateNotificationsEnabled(isEnabled: Boolean) {
        dataStore.updateData { it.copy(notificationEnabled = isEnabled) }
    }

    override suspend fun updatePassword(password: String) {
        val hashedPassword = hashPassword(password)
        dataStore.updateData { it.copy(passwordHash = hashedPassword) }
    }

    override suspend fun getPasswordHash(): String? {
        return dataStore.data.first().passwordHash
    }
}