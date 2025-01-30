package com.example.cnote.settings.presentation.settings_screen

sealed class SettingsEvent {
    data class DarkModeClicked(val isDark: Boolean) : SettingsEvent()
    data class NotificationsToggled(val enabled: Boolean) : SettingsEvent()
}