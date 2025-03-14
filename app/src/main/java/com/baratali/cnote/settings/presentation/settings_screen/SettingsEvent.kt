package com.baratali.cnote.settings.presentation.settings_screen

import com.baratali.cnote.settings.data.data_store.dto.DarkMode

sealed class SettingsEvent {
    data class DarkModeChanged(val darkMode: DarkMode) : SettingsEvent()
    data class NotificationsToggled(val enabled: Boolean) : SettingsEvent()
    object SetOrChangePasswordClicked : SettingsEvent()
}