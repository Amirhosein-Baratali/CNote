package com.baratali.cnote.settings.domain.repository

import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType
import com.baratali.cnote.settings.data.data_store.dto.DarkMode
import com.baratali.cnote.settings.data.data_store.dto.Settings
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getSettings(): Flow<Settings>
    suspend fun updateDarkMode(darkMode: DarkMode)
    suspend fun updateNotificationsEnabled(isEnabled: Boolean)
    suspend fun updatePassword(password: String)
    suspend fun getPasswordHash(): String?
    suspend fun updateDatePickerType(type: DatePickerType)
}