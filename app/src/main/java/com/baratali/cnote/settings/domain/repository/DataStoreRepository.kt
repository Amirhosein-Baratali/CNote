package com.baratali.cnote.settings.domain.repository

import com.baratali.cnote.settings.data.data_store.dto.DarkMode
import com.baratali.cnote.settings.data.data_store.dto.Settings
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getSettings(): Flow<Settings>
    suspend fun updateDarkMode(darkMode: DarkMode)
    suspend fun updateNotificationsEnabled(isEnabled: Boolean)
}