package com.baratali.cnote.core.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baratali.cnote.core.presentation.components.UiText
import com.baratali.cnote.core.presentation.components.snackbar.SnackbarAction
import com.baratali.cnote.core.presentation.components.snackbar.SnackbarController
import com.baratali.cnote.core.presentation.components.snackbar.SnackbarEvent
import com.baratali.cnote.core.presentation.components.snackbar.SnackbarType
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    fun showSnackbar(
        @StringRes messageId: Int,
        action: SnackbarAction? = null,
        snackbarType: SnackbarType = SnackbarType.NORMAL
    ) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = UiText.StringResource(messageId),
                    action = action,
                    snackbarType = snackbarType
                )
            )
        }
    }

    fun showSnackbar(
        message: String,
        action: SnackbarAction? = null,
        snackbarType: SnackbarType = SnackbarType.NORMAL
    ) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = UiText.DynamicString(message),
                    action = action,
                    snackbarType = snackbarType
                )
            )
        }
    }
}