package com.baratali.cnote.settings.data.data_store.dto

import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType
import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val darkMode: DarkMode = DarkMode.SystemDefault,
    val notificationEnabled: Boolean = false,
    val passwordHash: String? = null,
    val datePickerType: DatePickerType = DatePickerType.JALALI
)