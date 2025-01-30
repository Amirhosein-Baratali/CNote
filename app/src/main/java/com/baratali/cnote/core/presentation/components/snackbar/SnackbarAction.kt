package com.baratali.cnote.core.presentation.components.snackbar

import com.baratali.cnote.core.presentation.components.UiText

data class SnackbarAction(
    val name: UiText,
    val action: suspend () -> Unit
)