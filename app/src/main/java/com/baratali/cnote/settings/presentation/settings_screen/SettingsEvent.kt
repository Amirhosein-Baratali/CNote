package com.baratali.cnote.settings.presentation.settings_screen

import com.baratali.cnote.feature_task.presentation.add_edit_task.component.jalali_date_picker.DatePickerType
import com.baratali.cnote.settings.data.data_store.dto.DarkMode

sealed class SettingsEvent {
    data class DarkModeChanged(val darkMode: DarkMode) : SettingsEvent()
    data class NotificationsToggled(val enabled: Boolean) : SettingsEvent()
    object SetOrChangePasswordClicked : SettingsEvent()
    data class UpdateDatePickerType(val type: DatePickerType) : SettingsEvent()
}