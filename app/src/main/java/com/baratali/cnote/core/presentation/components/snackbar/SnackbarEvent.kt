package com.baratali.cnote.core.presentation.components.snackbar

import com.baratali.cnote.core.presentation.components.UiText

data class SnackbarEvent(
    val message: UiText,
    val action: SnackbarAction? = null,
    val snackbarType: SnackbarType = SnackbarType.NORMAL
)