package com.baratali.cnote.settings.domain.repository

import com.baratali.cnote.settings.data.data_store.dto.Settings
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getSettings(): Flow<Settings>
    suspend fun updateDarkMode(isDark: Boolean)
    suspend fun updateNotificationsEnabled(isEnabled: Boolean)
}