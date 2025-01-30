package com.example.cnote.settings.data.data_store.dto

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val isDark: Boolean? = null,
    val notificationEnabled: Boolean = false
)