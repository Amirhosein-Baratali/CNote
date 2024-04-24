package com.example.cnote.feature_task.presentation.add_edit_task

sealed class AddEditTaskEvent {
    data class EnteredName(val value: String) : AddEditTaskEvent()
    data class EnteredDescription(val value: String) : AddEditTaskEvent()
    object ToggleImportance : AddEditTaskEvent()
    object ToggleCompletion : AddEditTaskEvent()
    object SaveTask : AddEditTaskEvent()
}