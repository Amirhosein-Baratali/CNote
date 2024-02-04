package com.example.cnote.feature_task.presentation.add_edit_task

import androidx.compose.ui.focus.FocusState

sealed class AddEditTaskEvent {
    data class EnteredName(val value: String) : AddEditTaskEvent()
    data class ChangeNameFocus(val focusState: FocusState) : AddEditTaskEvent()
    data class EnteredDescription(val value: String) : AddEditTaskEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState) : AddEditTaskEvent()
    object ToggleImportance: AddEditTaskEvent()
    object ToggleCompletion: AddEditTaskEvent()
    object SaveTask : AddEditTaskEvent()
}