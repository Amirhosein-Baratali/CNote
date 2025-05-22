package com.baratali.cnote.settings.presentation.settings_screen

import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType
import com.baratali.cnote.settings.data.data_store.dto.DarkMode

data class SettingState(
    val darkMode: DarkMode = DarkMode.SystemDefault,
    val notificationsEnabled: Boolean = false,
    val passwordSet: Boolean = false,
    val datePickerType: DatePickerType = DatePickerType.JALALI
)