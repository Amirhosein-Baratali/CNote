package com.baratali.cnote.settings.presentation.password

import com.baratali.cnote.settings.presentation.util.PasswordMode

data class PasswordState(
    val password: String = "",
    val passwordHasError: Boolean = false,
    val changePasswordStep: ChangePasswordStep = ChangePasswordStep.EnterCurrent,
    val repeatPassword: String = "",
    val mode: PasswordMode = PasswordMode.SET_PASSWORD
) {
    val showRepeatPassword: Boolean
        get() = mode == PasswordMode.SET_PASSWORD
                || mode == PasswordMode.LOCK_NOTE_WITH_NEW_PASSWORD
                || changePasswordStep == ChangePasswordStep.SetNew

    val repeatPasswordHasError
        get() = repeatPassword.isNotEmpty() && repeatPassword != password

    val buttonEnabled: Boolean
        get() = password.isNotEmpty() && passwordHasError.not() && repeatPasswordHasError.not()

    val showBiometric: Boolean
        get() = mode == PasswordMode.UNLOCK_NOTE
                || mode == PasswordMode.OPEN_NOTE
}