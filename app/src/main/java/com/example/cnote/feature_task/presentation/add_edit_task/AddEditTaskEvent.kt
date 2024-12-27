package com.example.cnote.feature_task.presentation.add_edit_task

sealed class AddEditTaskEvent {
    data class EnteredName(val name: String) : AddEditTaskEvent()
    data class EnteredDescription(val description: String) : AddEditTaskEvent()
    object ToggleImportance : AddEditTaskEvent()
    object ToggleCompletion : AddEditTaskEvent()
    object SaveTask : AddEditTaskEvent()
    object Dismiss: AddEditTaskEvent()
}