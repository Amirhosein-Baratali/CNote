package com.baratali.cnote.settings.presentation.password

sealed class PasswordEvent {
    data class PasswordChanged(val password: String) : PasswordEvent()
    data class RepeatPasswordChanged(val repeatPassword: String) : PasswordEvent()
    object PasswordConfirmed : PasswordEvent()
    object Dismiss : PasswordEvent()
}