package com.example.cnote.settings.presentation.util

import kotlinx.serialization.Serializable

sealed class SettingScreens {
    @Serializable
    object Settings : SettingScreens()

    @Serializable
    object SettingList : SettingScreens()
}
