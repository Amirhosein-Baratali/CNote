package com.baratali.cnote.settings.data.data_store.dto

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val darkMode: DarkMode = DarkMode.SystemDefault,
    val notificationEnabled: Boolean = false
)