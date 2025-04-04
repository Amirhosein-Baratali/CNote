package com.baratali.cnote.settings.presentation.util

import kotlinx.serialization.Serializable

sealed class SettingScreens {
    @Serializable
    object Settings : SettingScreens()

    @Serializable
    object SettingList : SettingScreens()

    @Serializable
    data class Password(
        val mode: PasswordMode,
        val noteId: Int? = null
    ) : SettingScreens()
}