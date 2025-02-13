package com.baratali.cnote.settings.presentation.settings_screen

import com.baratali.cnote.settings.data.data_store.dto.DarkMode

data class SettingState(
    val darkMode: DarkMode = DarkMode.SystemDefault,
    val notificationsEnabled: Boolean = false
)